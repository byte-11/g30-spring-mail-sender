package uz.pdp.g30springmailmanager.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MailDto {
    private String from;
    private String to;
    private String subject;
    private String message;
}
