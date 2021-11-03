package com.edisonmoreno.infra.email;

import com.edisonmoreno.model.EmailBody;
import com.edisonmoreno.model.adapters.EmailPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailService implements EmailPort {

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    @Autowired
    private JavaMailSender sender;

    @Override
    public boolean sendEmail(EmailBody emailBody) {
        logger.info("EmailBody: {}", emailBody.toString());
        return sendEmailTool(emailBody.getContent(), emailBody.getEmail(), emailBody.getSubject());
    }


    private boolean sendEmailTool(String textMessage, String email, String subject) {
        boolean send = false;
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        try {
            helper.setTo(email);
            helper.setText(textMessage, true);
            helper.setSubject(subject);
            sender.send(message);
            send = true;
            logger.info("Mail enviado!");
        } catch (MessagingException e) {
            logger.error("Hubo un error al enviar el mail: {}", e);
        }
        return send;
    }
}
