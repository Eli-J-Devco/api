/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
*
*********************************************************/
package com.nwm.api.services;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.Clock;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.stereotype.Service;

import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.TenantBillingEntity;
import com.nwm.api.utils.Constants;
import com.nwm.api.utils.FLLogger;
import com.nwm.api.utils.Lib;
import com.nwm.api.utils.SendMail;

/**
 * Tenant Billing (WEBDEV-5686): sends the CSV of all meters (API 3) of a site by FTP or email,
 * on the receive date of each bems_tenant_billing_setting row.
 */
@Service
@SuppressWarnings("unchecked")
public class CronJobTenantBillingSendService extends DB {
	private static final FLLogger log = FLLogger.getLogger("batchjob/" + CronJobTenantBillingSendService.class.getSimpleName());

	// bems_tenant_billing_setting.type
	static final int TYPE_FTP = 1;
	static final int TYPE_EMAIL = 2;

	// no ftp_port on the setting: the default FTP port
	private static final int FTP_PORT = 21;
	// a server that does not answer fails the setting instead of blocking the job
	private static final int FTP_TIMEOUT_MS = 30000;

	// replaced in tests to get a fixed "now"
	Clock clock = Clock.systemUTC();

	TenantBillingService tenantBillingService = new TenantBillingService();

	public void execute() {
		try {
			// 1. every setting with a receive type (not 0: none)
			List<Map<String, Object>> settings = queryForList("BemsTenantBilling.getSendSettings", null);
			log.info("Tenant billing send: start, " + settings.size() + " setting(s)");
			int sent = 0, failed = 0;
			for (Map<String, Object> setting : settings) {
				try {
					if (send(setting)) sent++;
				} catch (Exception e) {
					failed++;
					log.error("Tenant billing send: failed, " + tag(setting) + ": " + e.getMessage(), e);
				}
			}
			log.info("Tenant billing send: end, sent=" + sent + ", failed=" + failed + ", skipped=" + (settings.size() - sent - failed));
		} catch (Exception e) {
			log.error("Tenant billing send error", e);
		}
	}

	// which setting a log line is about; never the password
	private static String tag(Map<String, Object> setting) {
		return "id setting=" + setting.get("id") + ", id site=" + setting.get("id_site") + ", type=" + setting.get("type");
	}

	/**
	 * @return true when the file was sent, false when today is not the receive day
	 */
	private boolean send(Map<String, Object> setting) throws Exception {
		// 2. only on the receive day of month, in the site time zone; a day the month does not have (e.g. 31 in April) runs on its last day
		ZoneId zone = ZoneId.of(setting.get("timezone") == null ? "UTC" : Lib.str(setting.get("timezone")));
		LocalDate today = LocalDate.now(clock.withZone(zone));
		if (setting.get("receive_date") == null) {
			log.info("Tenant billing send: skipped, no receive day, " + tag(setting));
			return false;
		}
		int day = ((Number) setting.get("receive_date")).intValue();
		if (today.getDayOfMonth() != Math.min(day, today.lengthOfMonth())) {
			log.info("Tenant billing send: skipped, receive day " + day + ", today " + today + " (" + zone + "), " + tag(setting));
			return false;
		}

		YearMonth month = YearMonth.from(today).minusMonths(1);
		TenantBillingEntity obj = new TenantBillingEntity();
		obj.setId_site(((Number) setting.get("id_site")).intValue());
		obj.setStart_date(month.atDay(1).toString());
		obj.setEnd_date(month.atEndOfMonth().toString());
		tenantBillingService.clock = clock;
		Map<String, Object> file = tenantBillingService.exportAll(obj);
		String fileName = Lib.str(file.get("file_name"));
		byte[] content = (byte[]) file.get("content");
		log.info("Tenant billing send: " + fileName + " (" + content.length + " bytes) for " + month + ", " + tag(setting));

		// 3. FTP: upload the file, email: send it attached
		int type = ((Number) setting.get("type")).intValue();
		if (type == TYPE_FTP) {
			uploadFtp(setting, fileName, content);
		} else if (type == TYPE_EMAIL) {
			sendEmail(setting, month, fileName, content);
		} else {
			throw new Exception("Unknown type " + type);
		}
		log.info("Tenant billing send: " + fileName + " sent, " + tag(setting));
		return true;
	}

	private void uploadFtp(Map<String, Object> setting, String fileName, byte[] content) throws Exception {
		String host = Lib.str(setting.get("ftp_host"));
		int port = setting.get("ftp_port") == null ? FTP_PORT : ((Number) setting.get("ftp_port")).intValue();
		String user = Lib.str(setting.get("ftp_user_name"));
		String server = user + "@" + host + ":" + port;

		String password;
		try {
			password = decryptPassword(Lib.str(setting.get("ftp_password")));
		} catch (Exception e) {
			throw new Exception("FTP password of " + server + " cannot be decrypted (key tenant.billing.aes.key)", e);
		}

		FTPClient ftp = new FTPClient();
		ftp.setConnectTimeout(FTP_TIMEOUT_MS);
		ftp.setDefaultTimeout(FTP_TIMEOUT_MS);
		ftp.setDataTimeout(FTP_TIMEOUT_MS);
		try {
			log.info("Tenant billing send: FTP connecting " + server);
			try {
				ftp.connect(host, port);
			} catch (Exception e) {
				throw new Exception("FTP connect to " + server + " failed: " + e, e);
			}
			if (!FTPReply.isPositiveCompletion(ftp.getReplyCode())) {
				throw new Exception("FTP connect to " + server + " refused: " + ftp.getReplyString().trim());
			}
			if (!ftp.login(user, password)) {
				throw new Exception("FTP login to " + server + " failed: " + ftp.getReplyString().trim());
			}
			log.info("Tenant billing send: FTP logged in " + server);
			ftp.enterLocalPassiveMode();
			ftp.setFileType(FTP.BINARY_FILE_TYPE);
			// TODO: remote folder; the home folder of the FTP user for now
			if (!ftp.storeFile(fileName, new ByteArrayInputStream(content))) {
				throw new Exception("FTP upload of " + fileName + " to " + server + " failed: " + ftp.getReplyString().trim());
			}
			log.info("Tenant billing send: FTP uploaded " + fileName + " to " + server);
			ftp.logout();
		} finally {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (Exception e) {
					log.error("Tenant billing send: FTP disconnect from " + server + " failed", e);
				}
			}
		}
	}

	/**
	 * ftp_password is encrypted by the FE with Libs.AESEncrypt (CryptoJS AES with a passphrase):
	 * base64 of "Salted__" + 8 bytes salt + AES-256-CBC data, key and iv from the passphrase and salt (OpenSSL EVP_BytesToKey, MD5)
	 */
	private String decryptPassword(String encrypted) throws Exception {
		byte[] passphrase = Lib.getReourcePropValue(Constants.appConfigFileName, "tenant.billing.aes.key").getBytes(StandardCharsets.UTF_8);
		byte[] data = Base64.getDecoder().decode(encrypted);
		byte[] salt = Arrays.copyOfRange(data, 8, 16);
		// key (32 bytes) + iv (16 bytes): MD5(previous block + passphrase + salt) repeated
		byte[] keyIv = new byte[48];
		byte[] block = new byte[0];
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		for (int i = 0; i < keyIv.length; i += block.length) {
			md5.update(block);
			md5.update(passphrase);
			block = md5.digest(salt);
			System.arraycopy(block, 0, keyIv, i, Math.min(block.length, keyIv.length - i));
		}
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(keyIv, 0, 32, "AES"), new IvParameterSpec(keyIv, 32, 16));
		return new String(cipher.doFinal(data, 16, data.length - 16), StandardCharsets.UTF_8);
	}

	private void sendEmail(Map<String, Object> setting, YearMonth month, String fileName, byte[] content) throws Exception {
		// SendMail attaches files from disk: the CSV goes to a temp folder under its own name
		Path dir = Files.createTempDirectory("tenant-billing");
		Path csv = dir.resolve(fileName);
		try {
			Files.write(csv, content);
			String siteName = Lib.str(setting.get("site_name"));
			log.info("Tenant billing send: email " + fileName + " to " + setting.get("receive_email"));
			boolean sent = SendMail.SendMailSMTPAttachment(siteName, Lib.str(setting.get("receive_email")), "", "",
				"Tenant billing " + siteName + " " + month, "Tenant billing of " + siteName + " for " + month + " is attached.", "",
				Collections.singletonList(csv.toString()));
			if (!sent) throw new Exception("Email to " + setting.get("receive_email") + " not sent; SendMail prints the cause to the console only");
		} finally {
			Files.deleteIfExists(csv);
			Files.deleteIfExists(dir);
		}
	}
}
