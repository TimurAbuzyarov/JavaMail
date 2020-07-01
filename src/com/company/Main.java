package com.company;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {
        String to = "to@mail.com";
        String from = "from@mail.com";
        String password = "password";
        String host = "smtp.mail.com";
        String port = "587";

        Properties properties = System.getProperties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.ssl.trust", host);

        Session session = Session.getDefaultInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject("Test Mail from Java");
            message.setText("Test Mail from Java");

            MimeBodyPart bodyPart = new MimeBodyPart();
            String fileName = "/pathToFile/file.png";
            FileDataSource source = new FileDataSource(fileName);
            bodyPart.setDataHandler(new DataHandler(source));
            bodyPart.setFileName(fileName);

            MimeMultipart multipart = new MimeMultipart();
            multipart.addBodyPart(bodyPart);
            message.setContent(multipart);

            Transport.send(message);
            System.out.println("Email Sent successfully...");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}