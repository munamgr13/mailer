package com.prot.mailer.controller;

import com.prot.mailer.dto.ContactUsRequest;
import com.prot.mailer.service.ContactUsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/contact")
public class Mail {

    @Autowired
    private ContactUsService contactUsService;

    @PostMapping("/send")
    public String sendContactMail(@RequestBody ContactUsRequest request) {
        try {
            System.out.println("Trying to send mail");
            contactUsService.sendContactUsEmail(request);
            return "Email sent successfully!";
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to send email: " + e.getMessage();
        }
    }
}



