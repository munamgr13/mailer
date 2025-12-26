package com.prot.mailer.service;


import com.prot.mailer.dto.ContactUsRequest;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;
import java.io.StringWriter;
import java.util.Properties;

@Service
public class ContactUsService {

    @Autowired
    private VelocityEngine velocityEngine;

    @Value("${email-notification.smtp-host}")
    private String smtpHost;

    @Value("${email-notification.smtp-port}")
    private Integer smtpPort;
    @Value("${email-notification.smtp-username}")
    private String smtpUsername;
    @Value("${email-notification.smtp-password}")
    private String smtpPassword;

    public void sendContactUsEmail(ContactUsRequest request) throws Exception {
        // 1. Prepare email body
        VelocityContext context = new VelocityContext();
        context.put("name", request.getName());
        context.put("email", request.getEmail());
        context.put("subject", request.getSubject());
        context.put("message", request.getMessage());

        StringWriter writer = new StringWriter();
        velocityEngine.mergeTemplate("velocity/contact-us.vm", "UTF-8", context, writer);
        String emailContent = writer.toString();

        // 2. Configure JavaMailSender
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(smtpHost);
        mailSender.setPort(smtpPort);
        mailSender.setUsername(smtpUsername);
        mailSender.setPassword(smtpPassword);

        Properties props = new Properties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        mailSender.setJavaMailProperties(props);

        // 3. Send email
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        helper.setFrom("dssuman525@gmail.com");
        helper.setTo("munamgr13@gmail.com"); // recipient
        helper.setSubject("New Contact Us Message - " + request.getSubject());
        helper.setText(emailContent, true); // true = HTML

        mailSender.send(mimeMessage);
    }
}
