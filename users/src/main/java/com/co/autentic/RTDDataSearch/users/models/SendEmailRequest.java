package com.co.autentic.RTDDataSearch.users.models;

public class SendEmailRequest {
    private EmailModel email;
    private EmailTypeEnum emailType;

    public EmailModel getEmail() {
        return email;
    }

    public void setEmail(EmailModel email) {
        this.email = email;
    }

    public EmailTypeEnum getEmailType() {
        return emailType;
    }

    public void setEmailType(EmailTypeEnum emailType) {
        this.emailType = emailType;
    }
}
