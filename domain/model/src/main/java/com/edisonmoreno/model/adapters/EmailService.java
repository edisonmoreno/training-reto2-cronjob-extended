package com.edisonmoreno.model.adapters;

import com.edisonmoreno.model.EmailBody;

public interface EmailService {
    boolean sendEmail(EmailBody emailBody);
}
