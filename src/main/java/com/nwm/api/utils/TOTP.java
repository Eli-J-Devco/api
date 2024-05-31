package com.nwm.api.utils;

import java.util.stream.IntStream;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * @description Generate Time-Based One-Time Password (Reference: RFC 6238)
 * @author Hung.Bui
 * @since 2024-05-28
 */
public class TOTP {
	private static final String HMAC_ALGO = "HmacSHA1";
	private static final int TOTP_LENGTH = 8;
	private static final int TIME_STEP = 60;
	private static final String sharedSecretKey = Lib.getReourcePropValue(Constants.appConfigFileName, Constants.privateKey);
	
	private static String generateTOTP(String userSecretKey, long timeInterval) {
		try {
			byte[] secretKey = sharedSecretKey.concat(userSecretKey).getBytes();
			byte[] timeIntervalBytes = new byte[8];

			// Convert the timeInterval into its byte representation
			for (int i = 7; i >= 0; i--) {
				timeIntervalBytes[i] = (byte) (timeInterval & 0xFF);
				timeInterval >>= 8;
			}
			
			Mac hmac = Mac.getInstance(HMAC_ALGO);
			hmac.init(new SecretKeySpec(secretKey, HMAC_ALGO));
			byte[] hash = hmac.doFinal(timeIntervalBytes);

			int offset = hash[hash.length - 1] & 0xF;

			long mostSignificantByte = (hash[offset] & 0x7F) << 24;
			long secondMostSignificantByte = (hash[offset + 1] & 0xFF) << 16;
			long thirdMostSignificantByte = (hash[offset + 2] & 0xFF) << 8;
			long leastSignificantByte = hash[offset + 3] & 0xFF;

			long binaryCode = mostSignificantByte | secondMostSignificantByte | thirdMostSignificantByte | leastSignificantByte;

			int totp = (int) (binaryCode % Math.pow(10, TOTP_LENGTH));
			return String.format("%0" + TOTP_LENGTH + "d", totp); // Making sure length is equal to TOTP_LENGTH
		} catch (Exception e) {
			return null;
		}
	}

	public static String generateTOTP(String userSecretKey) {
		long timeInterval = System.currentTimeMillis() / 1000 / TIME_STEP;
		return generateTOTP(userSecretKey, timeInterval);
    }

	public static boolean validateTOTP(String userSecretKey, String inputTOTP) {
		long timeInterval = System.currentTimeMillis() / 1000 / TIME_STEP;

		// Check TOTP for current, previous, and next intervals
		boolean matches = IntStream.of(-1, 0, 1).anyMatch(i -> generateTOTP(userSecretKey, timeInterval + i).equals(inputTOTP));
		if (matches) return true;
		return false;
    }
}
