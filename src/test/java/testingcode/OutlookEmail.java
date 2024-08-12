package testingcode;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import Configs.AppConfigs;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;



import io.cucumber.java.en.Given;

public class OutlookEmail {
	@Given("I want to send a email using outlook account")
	public void i_want_to_send_a_email_using_outlook_account() {
        final String username = AppConfigs.getOutlookUsername();
        final String password = AppConfigs.getOutlookPassword();

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.office365.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                        return new javax.mail.PasswordAuthentication(username, password);
                    }
                });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("ysatya@brileyfin.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("ysatya@brileyfin.com"));
            message.setSubject("Email Automation");

            // Create the message body part
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText("This email is sent for testing purpose using Selenium JavaMail");

            // Create the attachment body part
            MimeBodyPart attachmentBodyPart = new MimeBodyPart();
            String filePath = "C:\\Users\\ysatya\\Desktop\\sample.pdf";
            DataSource source = new FileDataSource(filePath);
            attachmentBodyPart.setDataHandler(new DataHandler(source));
            attachmentBodyPart.setFileName("TestingEvidence.pdf");
            

            
            // Create a multipart message
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            multipart.addBodyPart(attachmentBodyPart);
            // Set the content of the message
            message.setContent(multipart);

            // Send the message
            Transport.send(message);

            System.out.println("Email with attachment sent successfully!");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
	   }
}