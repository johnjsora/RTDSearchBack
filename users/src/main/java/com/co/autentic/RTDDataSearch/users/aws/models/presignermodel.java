package com.co.autentic.RTDDataSearch.users.aws.models;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;


public class presignermodel {

    private String proccessId;
    private String documentName;
    private String documentId;

    public String getProccessId() {
        return proccessId;
    }

    public void setProccessId(String proccessId) {
        this.proccessId = proccessId;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }
}
