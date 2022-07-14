package swag.rest.education_platform.email_client.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import swag.rest.education_platform.email_client.entity.Email;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailSenderServiceImpl implements EmailSenderService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendEmailWithAttachment(Email toEmail, String meetingLink) throws MessagingException {

        String body = "You invited to meeting " + meetingLink ;
        String subject = "Meeting";

        MimeMessage mimeMessage = mailSender.createMimeMessage();

        MimeMessageHelper mimeMessageHelper
                = new MimeMessageHelper(mimeMessage, true);

        mimeMessageHelper.setFrom("spring.email.from@gmail.com");
        mimeMessageHelper.setTo(toEmail.getEmail());
        mimeMessageHelper.setText(body);
        mimeMessageHelper.setSubject(subject);

        mailSender.send(mimeMessage);
        System.out.println("Mail Send...");

    }
}