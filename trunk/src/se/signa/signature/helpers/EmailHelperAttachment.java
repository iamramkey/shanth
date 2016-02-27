package se.signa.signature.helpers;

import java.io.File;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
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

import org.apache.log4j.Logger;

import se.signa.signature.common.Constants;
import se.signa.signature.common.SignatureException;
import se.signa.signature.gen.dba.EmailSettingsDba;
import se.signa.signature.gen.dbo.EmailSettings;

public class EmailHelperAttachment
{
	private static Logger logger = Logger.getLogger(EmailHelperAttachment.class);

	public static void main(String[] args)
	{
		String username = "noreplyalphastream@gmail.com";
		String password = "thraze1508jhs";
		String from = "alphastream@gmail.com";
		String to = "hemanth@thraze.in";
		String subject = "Test";
		StringBuffer content = new StringBuffer("Dear,\n\n Test : ");

		String filePath = "D://filename.txt";
		EmailSettings emailSettings = EmailSettingsDba.getI().getEmailSettings(Constants.ALERTMAIL_SETTINGS_ID);
		sendEmail(to, from, username, password, emailSettings, subject, content.toString(), filePath);
	}

	public static boolean sendNotification(String sendTo, String filePath)
	{
		EmailSettings emailSettings = EmailSettingsDba.getI().getEmailSettings(Constants.ALERTMAIL_SETTINGS_ID);
		String username = emailSettings.getEmsUserName();
		String password = emailSettings.getEmsPassword();
		String from = emailSettings.getEmsFromEmail();
		String to = sendTo;
		String subject = "Extraction Notification";
		StringBuffer content = new StringBuffer("Dear,\n\nNotification : ");
		content.append("Usage extraction is complete...\n\n");
		content.append("PFA");
		content.append("\n\nThanks \nStig Support");
		return sendEmail(to, from, username, password, emailSettings, subject, content.toString(), filePath);
	}

	public static boolean sendEmail(String to, String from, final String username, final String password, EmailSettings emailSettings, String subject, String content, String filePath)
	{
		Properties props = new Properties();
		props.put("mail.smtp.auth", emailSettings.getEmsSmtpAuth());
		props.put("mail.smtp.starttls.enable", emailSettings.getEmsSmtpStarttlsEnable());
		props.put("mail.smtp.host", emailSettings.getEmsSmtpHost());
		props.put("mail.smtp.port", String.valueOf(emailSettings.getEmsSmtpPort()));

		Session session = Session.getInstance(props, new javax.mail.Authenticator()
		{
			protected PasswordAuthentication getPasswordAuthentication()
			{
				return new PasswordAuthentication(username, password);
			}
		});

		try
		{
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			message.setSubject(subject);
			logger.info("Sending Email to :" + to);

			BodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setText(content);

			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);

			// Part two is attachment
			messageBodyPart = new MimeBodyPart();
			String fileLocalPath = filePath;
			File filename = new File(fileLocalPath);
			DataSource source = new FileDataSource(fileLocalPath);
			messageBodyPart.setDataHandler(new DataHandler(source));
			messageBodyPart.setFileName(filename.getName());
			multipart.addBodyPart(messageBodyPart);

			message.setContent(multipart);
			Transport.send(message);

			logger.info("Sent message successfully....");
			return true;

		}
		catch (MessagingException e)
		{
			throw new SignatureException(e);
		}
	}
}
