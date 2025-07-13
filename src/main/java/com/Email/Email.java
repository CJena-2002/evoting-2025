package com.Email;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class Email {
    // Define SMTP properties
    Properties properties = new Properties();
    properties.put("mail.smtp.auth", "true");
    properties.put("mail.smtp.starttls.enable", "true"); // Enable TLS
    properties.put("mail.smtp.host", "smtp.gmail.com"); // Correct SMTP server
    properties.put("mail.smtp.port", "587"); // Correct port for TLS

    // Gmail credentials
    final String myAccountEmail = "your-email@gmail.com";
    final String password = "your-password-or-app-password";

    // Create session
    Session session = Session.getInstance(properties, new Authenticator() {
        @Override
        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(myAccountEmail, password);
        }
    });

    // Prepare email
    Message message = prepareMessage(session, myAccountEmail, recipient);

    // Send email
    Transport.send(message);
    System.out.println("Message sent successfully");
}

private static Message prepareMessage(Session session, String myAccountEmail, String recipient) {
    try {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(myAccountEmail));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
        message.setSubject("My First Email");
        message.setText("Hey there, this is a test email from my Java application!");
        return message;
    } catch (Exception ex) {
        ex.printStackTrace();
    }
    return null;
}

public static void main(String[] args) {
    try {
        sendMail("recipient@example.com");
    } catch (Exception e) {
        e.printStackTrace();
    }
}
}
