package com.edisonmoreno.infra.email;

import com.edisonmoreno.model.EmailBody;
import com.edisonmoreno.model.adapters.EmailService;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class EmailServiceImpl implements EmailService {
    private static final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);

    @Override
    public boolean sendEmail(EmailBody emailBody) {
        logger.info("EmailBody: {}", emailBody.toString());
        String SENDGRID_API_KEY = "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";
        return send(emailBody, SENDGRID_API_KEY);
    }

    private boolean send(EmailBody emailBody, String apiKey) {
        Email from = new Email("edisonmoreno@gmail.com");
        Email to = new Email(emailBody.getEmail());

        String subject = emailBody.getSubject();
        Content content = new Content("text/html", emailBody.getContent());

        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid(apiKey);
        Request request = new Request();

        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());

            Response response = sg.api(request);


            logger.info(String.valueOf(response.getStatusCode()));
            logger.info(String.valueOf(response.getHeaders()));
            logger.info(response.getBody());

        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
}
