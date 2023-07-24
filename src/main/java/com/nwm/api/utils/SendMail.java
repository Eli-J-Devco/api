package com.nwm.api.utils;

import java.io.File;
import java.util.Date;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.sun.mail.smtp.SMTPTransport;

public class SendMail {

	public static boolean SendGmailTLSAttachmentattachment(String mail_from, String from_name, String mail_to, String subject, String body, String tags, String file) throws Exception {
        String HOST = Lib.getReourcePropValue(Constants.mailConfigFileName, Constants.mailSmtpServer);
        int PORT = Lib.strToInteger(Lib.getReourcePropValue(Constants.mailConfigFileName, Constants.mailPort));
        String AUTH = Lib.getReourcePropValue(Constants.mailConfigFileName, Constants.mailAuth);
        String  TLS = Lib.getReourcePropValue(Constants.mailConfigFileName, Constants.mailTLS);
        final String SMTP_USERNAME = Lib.getReourcePropValue(Constants.mailConfigFileName, Constants.mailUserName);	
        final String SMTP_PASSWORD = Lib.getReourcePropValue(Constants.mailConfigFileName, Constants.mailPassword);
		
        Properties prop = new Properties();
        prop.put("mail.smtp.host", HOST);
        prop.put("mail.smtp.port", PORT);
        prop.put("mail.smtp.auth", AUTH);
        prop.put("mail.smtp.starttls.enable", TLS); //TLS
        boolean flg;
        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(SMTP_USERNAME, SMTP_PASSWORD);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(mail_from));
            message.setRecipients(
                    Message.RecipientType.BCC,
                    InternetAddress.parse(mail_to)
            );
            message.setSubject(subject);
            BodyPart messageBodyPart = new MimeBodyPart(); 
            messageBodyPart.setText("Mail Body");
            messageBodyPart.setContent(body, "text/html");
            
            MimeBodyPart attachmentPart = new MimeBodyPart();
            attachmentPart.attachFile(new File(file));
            
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            multipart.addBodyPart(attachmentPart);
            
            message.setContent(multipart);
            
            Transport.send(message);
            flg = true;

        } catch (MessagingException e) {
            e.printStackTrace();
            flg = false;
        }
        
        return flg;
	}
	 
	public static boolean SendGmailTLS(String mail_from, String from_name, String mail_to, String mail_to_cc, String mail_to_bcc, String subject, String body, String tags) throws Exception {
        String HOST = Lib.getReourcePropValue(Constants.mailConfigFileName, Constants.mailSmtpServer);
        int PORT = Lib.strToInteger(Lib.getReourcePropValue(Constants.mailConfigFileName, Constants.mailPort));
        String AUTH = Lib.getReourcePropValue(Constants.mailConfigFileName, Constants.mailAuth);
        String  TLS = Lib.getReourcePropValue(Constants.mailConfigFileName, Constants.mailTLS);
        final String SMTP_USERNAME = Lib.getReourcePropValue(Constants.mailConfigFileName, Constants.mailUserName);	
        final String SMTP_PASSWORD = Lib.getReourcePropValue(Constants.mailConfigFileName, Constants.mailPassword);
		
        Properties prop = new Properties();
        prop.put("mail.smtp.host", HOST);
        prop.put("mail.smtp.port", PORT);
        prop.put("mail.smtp.auth", AUTH);
        prop.put("mail.smtp.starttls.enable", TLS); //TLS
        boolean flg;
        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(SMTP_USERNAME, SMTP_PASSWORD);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(mail_from));
            
            if(mail_to != null) {
            	message.addRecipients(
                        Message.RecipientType.TO,
                        InternetAddress.parse(mail_to)
                );
            }
            
            if(mail_to_cc != null) {
            	message.addRecipients(
                        Message.RecipientType.CC,
                        InternetAddress.parse(mail_to_cc)
                );
            }
            
            if(mail_to_bcc != null) {
            	message.addRecipients(
                        Message.RecipientType.BCC,
                        InternetAddress.parse(mail_to_bcc)
                );
            }
            
            message.setSubject(subject);
            message.setContent(body, "text/html");
            
            Transport.send(message);
            flg = true;
            System.out.println("Done");

        } catch (MessagingException e) {
            e.printStackTrace();
            flg = false;
        }
        
        return flg;
	}
	
	
	 
	public static boolean SendGmailSSL(String mail_from, String from_name, String mail_to, String subject, String body, String tags) throws Exception {
		String HOST = Lib.getReourcePropValue(Constants.mailConfigFileName, Constants.mailSmtpServer);
        int PORT = Lib.strToInteger(Lib.getReourcePropValue(Constants.mailConfigFileName, Constants.mailPort));
        String AUTH = Lib.getReourcePropValue(Constants.mailConfigFileName, Constants.mailAuth);
        final String SMTP_USERNAME = Lib.getReourcePropValue(Constants.mailConfigFileName, Constants.mailUserName);	
        final String SMTP_PASSWORD = Lib.getReourcePropValue(Constants.mailConfigFileName, Constants.mailPassword);

        Properties prop = new Properties();
        prop.put("mail.smtp.host", HOST);
        prop.put("mail.smtp.port", PORT);
        prop.put("mail.smtp.auth", AUTH);
        prop.put("mail.smtp.socketFactory.port", PORT);
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        boolean flg;
        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(SMTP_USERNAME, SMTP_PASSWORD);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(mail_from));
            message.setRecipients(
                    Message.RecipientType.BCC,
                    InternetAddress.parse(mail_to)
            );
            message.setSubject(subject);
            message.setContent(body, "text/html");
            Transport.send(message);
            flg = true;

        } catch (MessagingException e) {
            e.printStackTrace();
            flg = false;
        }
        return flg;
	}
	public static boolean mailSMTPAmazon(String mail_from, String from_name, String mail_to, String subject, String body, String tags) throws Exception {
		// Amazon SES SMTP host name. This example uses the US West (Oregon) region.
		String HOST = Lib.getReourcePropValue(Constants.mailConfigFileName, Constants.mailSmtpServer);
		// Replace smtp_username with your Amazon SES SMTP user name.
		final String SMTP_USERNAME = Lib.getReourcePropValue(Constants.mailConfigFileName, Constants.mailUserName);	
		// Replace smtp_password with your Amazon SES SMTP password.
		final String SMTP_PASSWORD = Lib.getReourcePropValue(Constants.mailConfigFileName, Constants.mailPassword);;
		int PORT = Lib.strToInteger(Lib.getReourcePropValue(Constants.mailConfigFileName, Constants.mailPort));
		String AUTH = Lib.getReourcePropValue(Constants.mailConfigFileName, Constants.mailAuth);
		String  PROTOCAL = Lib.getReourcePropValue(Constants.mailConfigFileName, Constants.mailProtocol);
		String  SSL = Lib.getReourcePropValue(Constants.mailConfigFileName, Constants.mailSSL);
		String  TLS = Lib.getReourcePropValue(Constants.mailConfigFileName, Constants.mailTLS);
		boolean flg;

		Properties props = System.getProperties();
		props.put("mail.transport.protocol", PROTOCAL);
		props.put("mail.smtp.port", PORT);
		props.put("mail.smtp.starttls.enable", TLS);
		props.put("mail.smtp.auth", AUTH);
		props.put("mail.smtp.ssl.enable", SSL);
		props.put("mail.smtp.localhost", HOST);

		// Create a Session object to represent a mail session with the specified
		// properties.
		Session session = Session.getDefaultInstance(props);
		session.setDebug(true);
		// Create a message with the specified information.
		MimeMessage msg = new MimeMessage(session);
		msg.setFrom(new InternetAddress(mail_from, from_name));
		msg.setRecipient(Message.RecipientType.BCC, new InternetAddress(mail_to));
		msg.setSubject(subject);
		msg.setContent(body, "text/html");

		// Create a transport.
		Transport transport = session.getTransport();

		try {
			// Connect to Amazon SES using the SMTP username and password you specified
			transport.connect(HOST, SMTP_USERNAME, SMTP_PASSWORD);

			// Send the email.
			transport.sendMessage(msg, msg.getAllRecipients());
			System.out.println("Sent.");
			flg = true;
		} catch (Exception ex) {
			flg = false;
			System.out.println("The email was not sent.");
			System.out.println("Error message: " + ex.getMessage());
		} finally {
			// Close and terminate the connection.
			transport.close();
		}
		return flg;
	}

	public static boolean mailCritsend(String mail_from, String mail_to, String subject, String body, String tags)
			throws Exception {
		boolean flg;
		try {
			String mail_smtp_server = Lib.getReourcePropValue(Constants.mailConfigFileName, Constants.mailSmtpServer);
			final String login = Lib.getReourcePropValue(Constants.mailConfigFileName, Constants.mailUserName);
			final String pwd = Lib.getReourcePropValue(Constants.mailConfigFileName, Constants.mailPassword);
			int port = Lib.strToInteger(Lib.getReourcePropValue(Constants.mailConfigFileName, Constants.mailPort));
			String mail_auth = Lib.getReourcePropValue(Constants.mailConfigFileName, Constants.mailAuth);
			String is_debug = Lib.getReourcePropValue(Constants.mailConfigFileName, Constants.mailDebug);

			Properties props = System.getProperties();
			props.put("mail.smtps.host", mail_smtp_server);
			props.put("mail.smtps.auth", mail_auth);
			props.put("mail.smtps.port", port);
			props.put("mail.debug", is_debug);
			Session session = Session.getInstance(props, null);
			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(mail_from));
			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mail_to, false));
			msg.addRecipient(Message.RecipientType.BCC, new InternetAddress("lpham@nextwavemonitoring.com"));
			msg.setSubject(subject);
			// msg.setText(body);
			msg.setContent(body, "text/html;charset=\"UTF-8\"");
			msg.setHeader("X-Mailer", "Header");
			// add custom tags
			msg.addHeader("X-Tag", tags + ",bulk");
			msg.setSentDate(new Date());
			SMTPTransport t = (SMTPTransport) session.getTransport("smtps");
			t.connect(mail_smtp_server, login, pwd);
			t.sendMessage(msg, msg.getAllRecipients());
			System.out.println("Response: " + t.getLastServerResponse());
			t.close();
			flg = true;
			System.out.println("SendMail:Message sent OK.");
		} catch (MessagingException e) {
			System.out.println("SendMail:Message sent NG.");
			e.printStackTrace();
			flg = false;
		}
		return flg;
	}
}
