package com.inventrymanagement.inventry.service;

import com.inventrymanagement.inventry.entity.Product;
import jakarta.annotation.PostConstruct;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Properties;

@Service
public class NotificationService {

    @Value("${admin.notification.mail}")
    private String to;
    private Properties prop;
    private Session session;

    @PostConstruct
    public void init(){
        prop=new Properties();
        prop.put("mail.smtp.host","smtp.gmail.com");
        prop.put("mail.smtp.port","465");
        prop.put("mail.smtp.auth","true");
        prop.put("mail.smtp.ssl.enable","true");
        prop.put("mail.smtp.ssl.trust","*");
        Authenticator authenticator=new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("****","****");
            }
        };
        session=Session.getInstance(prop,authenticator);
    }

    @Async(value = "taskExecutor")
    public void sendAlert(List<Product> productList) throws MessagingException {
        MimeMessage mimeMessage=new MimeMessage(session);
        mimeMessage.setRecipient(Message.RecipientType.TO,new InternetAddress(to));
        mimeMessage.setContent(getEmailBody(productList), "text/html");
        Transport.send(mimeMessage);
    }

    public String getEmailBody(List<Product> productList) {
        StringBuilder emailBody = new StringBuilder();

        emailBody.append("<html><body>");
        emailBody.append("<h2>Inventory Alert: Low Product Quantity</h2>");
        emailBody.append("<p>Dear Admin,</p>");
        emailBody.append("<p>The following products have low stock levels:</p>");


        emailBody.append("<table border='1' style='border-collapse: collapse;'>");
        emailBody.append("<tr>");
        emailBody.append("<th style='padding: 8px; text-align: left;'>Product Title</th>");
        emailBody.append("<th style='padding: 8px; text-align: left;'>Price</th>");
        emailBody.append("<th style='padding: 8px; text-align: left;'>Quantity</th>");
        emailBody.append("<th style='padding: 8px; text-align: left;'>Description</th>");
        emailBody.append("</tr>");

        for (Product product : productList) {
            emailBody.append("<tr>");
            emailBody.append("<td style='padding: 8px;'>").append(product.getTitle()).append("</td>");
            emailBody.append("<td style='padding: 8px;'>").append(product.getPrice()).append("</td>");
            emailBody.append("<td style='padding: 8px;'>").append(product.getQty()).append("</td>");
            emailBody.append("<td style='padding: 8px;'>").append(product.getDescription()).append("</td>");
            emailBody.append("</tr>");
        }

        emailBody.append("</table>");

        emailBody.append("<p>Please take necessary action to replenish stock.</p>");
        emailBody.append("<p>Best Regards,<br/>Your Inventory Management System</p>");
        emailBody.append("</body></html>");

        return emailBody.toString();
    }

}
