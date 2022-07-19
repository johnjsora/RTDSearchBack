package com.co.autentic.RTDDataSearch.users.models;

import java.util.List;

public class EmailModel {
    private String recipientEmail;
    private List<String> recipientEmails;
    private String subject;
    private String body;
    private List<S3FileModel> files;
    private List<AttachedFileModel> attachedFiles;
    private String enterprise;

    public String getRecipientEmail() {
        return recipientEmail;
    }

    public void setRecipientEmail(String recipientEmail) {
        this.recipientEmail = recipientEmail;
    }

    public List<String> getRecipientEmails() {
        return recipientEmails;
    }

    public void setRecipientEmails(List<String> recipientEmails) {
        this.recipientEmails = recipientEmails;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public List<S3FileModel> getFiles() {
        return files;
    }

    public void setFiles(List<S3FileModel> files) {
        this.files = files;
    }

    public List<AttachedFileModel> getAttachedFiles() {
        return attachedFiles;
    }

    public void setAttachedFiles(List<AttachedFileModel> attachedFiles) {
        this.attachedFiles = attachedFiles;
    }

    public String getEnterprise() {
        return enterprise;
    }

    public void setEnterprise(String enterprise) {
        this.enterprise = enterprise;
    }
}
