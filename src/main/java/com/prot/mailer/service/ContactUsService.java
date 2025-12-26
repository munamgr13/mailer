package com.prot.mailer.service;

import com.prot.mailer.dto.ContactUsRequest;
import com.resend.Resend;
import com.resend.services.emails.model.CreateEmailOptions;
import com.resend.services.emails.model.CreateEmailResponse;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.StringWriter;

@Service
public class ContactUsService {

    private final VelocityEngine velocityEngine;

    @Value("${resend.api-key}")
    private String resendApiKey;  // Use the API key here

    @Value("${resend.from-email:no-reply@resend.dev}") // default fallback
    private String fromEmail;

    @Value("${resend.to-email}")
    private String toEmail;

    public ContactUsService(VelocityEngine velocityEngine) {
        this.velocityEngine = velocityEngine;
    }

    public void sendContactUsEmail(ContactUsRequest request) throws Exception {

        // 1. Render the email body using Velocity
        VelocityContext context = new VelocityContext();
        context.put("name", request.getName());
        context.put("email", request.getEmail());
        context.put("subject", request.getSubject());
        context.put("message", request.getMessage());

        StringWriter writer = new StringWriter();
        velocityEngine.mergeTemplate("velocity/contact-us.vm", "UTF-8", context, writer);
        String htmlContent = writer.toString();

        // 2. Initialize Resend client with API key
        Resend client = new Resend(resendApiKey);

        // 3. Build the email request
        CreateEmailOptions emailOptions = CreateEmailOptions.builder()
                .from(fromEmail)              // must be verified domain or no-reply@resend.dev
                .to(toEmail)                  // recipient
                .subject("New Contact Us Message - " + request.getSubject())
                .html(htmlContent)
                .build();

        // 4. Send email
        CreateEmailResponse response = client.emails().send(emailOptions);

        // Optional: log email ID
        System.out.println("Sent email ID: " + response.getId());
    }
}
