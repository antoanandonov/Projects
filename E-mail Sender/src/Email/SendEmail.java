package Email;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.JOptionPane;

public class SendEmail
{
	private int port;
	private String hostName;
	private String from;
	private String password;
	private String to;
	private String subject;
	private String message;
	private static String file;

	public SendEmail(String from, String password, String to, String subject, String message)
	{
		setFrom(from);
		setHostName(from);
		setPassword(password);
		setTo(to);
		setSubject(subject);
		setMessage(message);
	}

	public SendEmail(String from, String password, String to, String subject, String message, String file)
	{
		setFrom(from);
		setHostName(from);
		setPassword(password);
		setTo(to);
		setSubject(subject);
		setMessage(message);
		try
		{
			setFile(file);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	private void setPort(int port)
	{
		if(port < 0)
		{
			JOptionPane.showMessageDialog(null, new IllegalArgumentException("Invalid port!"), "IllegalArgumentException", JOptionPane.ERROR_MESSAGE);
			return;
		}
		this.port = port;
	}

	private void setHostName(String hostName)
	{
		if(hostName.endsWith("yahoo.com"))
		{
			this.hostName = "smtp.mail.yahoo.com";
			setPort(587);
		}
		else if(hostName.endsWith("gmail.com"))
		{
			this.hostName = "smtp.gmail.com";
			setPort(587);
		}
		else if(hostName.endsWith("icloud.com"))
		{
			this.hostName = "smtp.mail.me.com";
			setPort(587);
		}
		else if(hostName.endsWith("abv.bg"))
		{
			this.hostName = "smtp.abv.bg";
			setPort(465);
		}
		else if(hostName.endsWith("mail.bg"))
		{
			this.hostName = "smtp.mail.bg";
			setPort(110);
		}
		else if(hostName.endsWith("live.com"))
		{
			this.hostName = "smtp.live.com";
			setPort(465);
		}
		else if(hostName.endsWith("outlook.com"))
		{
			this.hostName = "smtp-mail.outlook.com";
			setPort(587);
		}
		else if(hostName.endsWith("office365.com"))
		{
			this.hostName = "smtp.office365.com";
			setPort(587);
		}
		else if(hostName.endsWith("aol.com"))
		{
			this.hostName = "smtp.aol.com";
			setPort(587);
		}
	}

	private void setPassword(String password)
	{
		if(!password.matches("^(?=\\S+$).{6,}$"))
		{
			JOptionPane.showMessageDialog(null, new IllegalArgumentException("Invalid password!"), "IllegalArgumentException", JOptionPane.ERROR_MESSAGE);
			return;
		}
		this.password = password;
	}

	private void setFrom(String from)
	{
		if(!from.matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"))
		{
			JOptionPane.showMessageDialog(null, new IllegalArgumentException("Check 'From' e-mail address!"), "IllegalArgumentException", JOptionPane.ERROR_MESSAGE);
			return;
		}
		this.from = from;
	}

	private void setTo(String to)
	{
		if(!to.matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"))
		{
			JOptionPane.showMessageDialog(null, new IllegalArgumentException("Check 'To' e-mail address!"), "IllegalArgumentException", JOptionPane.ERROR_MESSAGE);
			return;
		}
		this.to = to;
	}

	private void setSubject(String subject)
	{
		this.subject = subject;
	}

	private void setMessage(String message)
	{
		this.message = message;
	}

	private static void setFile(String file) throws IOException
	{
		if(file == null)
		{
			JOptionPane.showMessageDialog(null, new IOException("No such directory!"), "IOException", JOptionPane.ERROR_MESSAGE);
		}
		SendEmail.file = file;
	}

	private int getPort()
	{
		return this.port;
	}

	private String getHostName()
	{
		return this.hostName;
	}

	private String getPassword()
	{
		return this.password;
	}

	private String getFrom()
	{
		return this.from;
	}

	private String getTo()
	{
		return this.to;
	}

	private String getSubject()
	{
		return this.subject;
	}

	private String getMessage()
	{
		return this.message;
	}

	private String getFile()
	{
		return SendEmail.file;
	}

	public void sendEmailWithAttachment() throws AddressException, MessagingException
	{
		// sets SMTP server properties
		Properties properties = new Properties();
		properties.put("mail.smtp.host", getHostName());
		properties.put("mail.smtp.port", getPort());
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.user", getFrom());
		properties.put("mail.password", getPassword());

		// creates a new session with an authenticator
		Authenticator auth = new Authenticator()
		{
			public PasswordAuthentication getPasswordAuthentication()
			{
				return new PasswordAuthentication(getFrom(), getPassword());
			}
		};
		Session session = Session.getInstance(properties, auth);

		// creates a new e-mail message
		Message msg = new MimeMessage(session);

		msg.setFrom(new InternetAddress(getFrom()));
		InternetAddress[] toAddresses = { new InternetAddress(getTo()) };
		msg.setRecipients(Message.RecipientType.TO, toAddresses);
		msg.setSubject(getSubject());
		msg.setText(getMessage());
		msg.setSentDate(new Date());

		// creates message part
		MimeBodyPart messageBodyPart = new MimeBodyPart();
		messageBodyPart.setContent(getMessage(), "text/html");

		// creates multi-part
		Multipart multipart = new MimeMultipart();

		DataSource source = new FileDataSource(getFile());
		messageBodyPart.setDataHandler(new DataHandler(source));
		messageBodyPart.setFileName(getFile());

		multipart.addBodyPart(messageBodyPart);

		// Send the complete message parts
		msg.setContent(multipart);

		// sends the e-mail
		Transport.send(msg);
	}

	public void sendEmailWithoutAttachment() throws AddressException, MessagingException
	{
		// sets SMTP server properties
		Properties properties = new Properties();
		properties.put("mail.smtp.host", getHostName());
		properties.put("mail.smtp.port", getPort());
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.user", getFrom());
		properties.put("mail.password", getPassword());

		// creates a new session with an authenticator
		Authenticator auth = new Authenticator()
		{
			public PasswordAuthentication getPasswordAuthentication()
			{
				return new PasswordAuthentication(getFrom(), getPassword());
			}
		};
		Session session = Session.getInstance(properties, auth);

		// creates a new e-mail message
		Message msg = new MimeMessage(session);

		msg.setFrom(new InternetAddress(getFrom()));
		InternetAddress[] toAddresses = { new InternetAddress(getTo()) };
		msg.setRecipients(Message.RecipientType.TO, toAddresses);
		msg.setSubject(getSubject());
		msg.setText(getMessage());
		msg.setSentDate(new Date());

		// creates message part
		MimeBodyPart messageBodyPart = new MimeBodyPart();
		messageBodyPart.setContent(getMessage(), "text/html");

		// creates multi-part
		Multipart multipart = new MimeMultipart();

		multipart.addBodyPart(messageBodyPart);

		// Send the complete message parts
		msg.setContent(multipart);

		// sends the e-mail
		Transport.send(msg);
	}

}
