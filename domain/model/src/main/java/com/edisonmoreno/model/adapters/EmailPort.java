package com.edisonmoreno.model.adapters;

import com.edisonmoreno.model.EmailBody;

public interface EmailPort {
    public boolean sendEmail(EmailBody emailBody);
}
