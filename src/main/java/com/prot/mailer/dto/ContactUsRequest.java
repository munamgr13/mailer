package com.prot.mailer.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
public class ContactUsRequest {
    private String name;
    private String email;
    private String subject;
    private String message;

}
