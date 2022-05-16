package Model;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
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

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class Mail
{
	private static Logger log = LogManager.getLogger(Mail.class);
	
	public boolean sendMail(String[] attachments, String to, String subject, String body)
	{   
		
		MailOptions mailOptions = new MailOptions();
		return sendMail(attachments, to, subject, body, mailOptions);
	}

	public boolean sendMail(String[] attachments, String to, String subject, String body, MailOptions mailoptions)
	{
		boolean sent = false;

		if (mailoptions == null)
			mailoptions = new MailOptions();
		Properties properties = System.getProperties();
		properties.setProperty("mail.smtp.host", mailoptions.getHost());
		log.debug("Mail welcomeLetter host="+ mailoptions.getHost());
		properties.setProperty("mail.smtp.port", mailoptions.getPort());// change
		log.debug("Mail welcomeLetter port="+ mailoptions.getPort());
		properties.put("mail.smtp.auth", mailoptions.getAuthenticate());
		properties.put("mail.smtp.user", mailoptions.getMailUser());
		log.debug("Mail welcomeLetter Username="+ mailoptions.getMailUser());
		properties.put("mail.smtp.ssl.enable", mailoptions.getSsl());
		log.debug("Mail welcomeLetter SSL="+ mailoptions.getSsl());

		Authenticator auth = new SMTPAuthenticator(mailoptions);
		Session sessionmail = Session.getInstance(properties, auth);

		// 2) compose message
		try
		{
			MimeMessage message = new MimeMessage(sessionmail);
			message.setFrom(new InternetAddress(mailoptions.getMailUser()));
			log.debug("Mail welcomeLetter Username1="+ mailoptions.getMailUser());
			message.setRecipients(Message.RecipientType.TO, to);
			log.debug("Mail welcomeLetter To Mail="+ to);
			message.setSubject(subject);
			sessionmail.setDebug(mailoptions.isMailDebug());

			// 3) create MimeBodyPart object and set your message content
			BodyPart messageBodyPart1 = new MimeBodyPart();
			messageBodyPart1.setContent(body, "text/html; charset=utf-8");

			// 5) create Multipart object and add MimeBodyPart objects to this
			// object
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart1);
			if (attachments != null && attachments.length > 0)
			{
				for (int i = 0; i < attachments.length; i++)
				{
					log.debug("Attaching " + attachments[i]);
					if (attachments[i] != null)
						multipart.addBodyPart(addAttachment(attachments[i]));
				}
			}
			// 6) set the multiplart object to the message object
			message.setContent(multipart);

			// 7) send message
			log.debug("Sending mail");
			Transport.send(message);
			log.debug("Sending mail done");
			//this.mailOptions=null;
			sessionmail = null;
			sent = true;
		}
		catch (MessagingException ex)
		{
			ex.printStackTrace();
		}
		return sent;
	}
	
	// send mail To Email Address with CC also --- karthik
	
	public boolean sendMail(String[] attachments, String to, String tocc, String subject, String body, MailOptions mailoptions)
	{
		boolean sent = false;

		if (mailoptions == null)
			mailoptions = new MailOptions();

		Properties properties = System.getProperties();
		properties.setProperty("mail.smtp.host", mailoptions.getHost());
		properties.setProperty("mail.smtp.port", mailoptions.getPort());// change
		properties.put("mail.smtp.auth", mailoptions.getAuthenticate());
		properties.put("mail.smtp.user", mailoptions.getMailUser());
		properties.put("mail.smtp.ssl.enable", mailoptions.getSsl());
		
		Authenticator auth = new SMTPAuthenticator(mailoptions);
		Session sessionmail = Session.getInstance(properties, auth);

		// 2) compose message
		try
		{
			MimeMessage message = new MimeMessage(sessionmail);
			message.setFrom(new InternetAddress(mailoptions.getMailUser()));
			message.setRecipients(Message.RecipientType.TO, to);
			log.debug("Compose Corpus To:" + to);
			message.setRecipients(Message.RecipientType.CC, tocc);
			log.debug("Compose Corpus ToCC:" + tocc);
			message.setSubject(subject);
			log.debug("Compose Corpus Subject:" + subject);
			sessionmail.setDebug(mailoptions.isMailDebug());
			log.debug("Compose Corpus MailDebug:" + mailoptions.isMailDebug() );

			// 3) create MimeBodyPart object and set your message content
			BodyPart messageBodyPart1 = new MimeBodyPart();
			messageBodyPart1.setContent(body, "text/html; charset=utf-8");

			// 5) create Multipart object and add MimeBodyPart objects to this
			// object
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart1);
			if (attachments != null && attachments.length > 0)
			{
				for (int i = 0; i < attachments.length; i++)
				{
					log.debug("Attaching " + attachments[i]);
					if (attachments[i] != null)
						multipart.addBodyPart(addAttachment(attachments[i]));
				}
			}
			// 6) set the multiplart object to the message object
			message.setContent(multipart);

			// 7) send message
			log.debug("Sending mail");
			Transport.send(message);
			log.debug("Sending mail done");
			//this.mailOptions=null;
			sessionmail = null;
			sent = true;
		}
		catch (MessagingException ex)
		{
			ex.printStackTrace();
		}
		return sent;
	}
	private MimeBodyPart addAttachment(String filename) throws MessagingException
	{
		DataSource source = new FileDataSource(filename);
		MimeBodyPart messageBodyPart2 = new MimeBodyPart();
		messageBodyPart2.setDataHandler(new DataHandler(source));
		messageBodyPart2.setFileName(source.getName());
		return messageBodyPart2;
	}

	private class SMTPAuthenticator extends javax.mail.Authenticator
	{
		private MailOptions mailOptions;

		public SMTPAuthenticator(MailOptions mailoptions)
		{
			this.mailOptions = mailoptions;
			//mailOptions = mailoptions;
		}

		public PasswordAuthentication getPasswordAuthentication()
		{
			log.debug("SMTP Password Authenticator:"+ this.mailOptions.getMailUser());
			log.debug("SMTP Password Authenticator:"+ this.mailOptions.getMailPassword());
			return new PasswordAuthentication(this.mailOptions.getMailUser(), this.mailOptions.getMailPassword());
		}
	}

	public static void main(String[] args)
	{
		Mail m = new Mail();
		String x[] = new String[2];
		x[0] = "C:/Prasad/workspace/Prolec/.classpath";
		x[1] = "C:/Prasad/workspace/Prolec/.project";

		m.sendMail(x, "tnprasad@gmail.com", "hello", "testing");
	}
}
