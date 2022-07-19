package com.co.autentic.RTDDataSearch.users.models;

public class ResponseDocuments extends Responsemodel{
    private String documentBase;
    private String documentName;
    private String documentExtention;

    public String getDocumentBase() {
        return documentBase;
    }

    public void setDocumentBase(String documentBase) {
        this.documentBase = documentBase;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public String getDocumentExtention() {
        return documentExtention;
    }

    public void setDocumentExtention(String documentExtention) {
        this.documentExtention = documentExtention;
    }
}
