package swag.rest.education_platform.email_client.service;


import org.springframework.stereotype.Service;
import swag.rest.education_platform.email_client.entity.Email;

import javax.mail.MessagingException;
@Service
public interface EmailSenderService {
    void sendEmailWithAttachment(Email toEmail, String meetingLink) throws MessagingException;

}
