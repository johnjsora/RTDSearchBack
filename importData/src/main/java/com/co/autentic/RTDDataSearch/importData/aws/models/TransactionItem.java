package com.co.autentic.RTDDataSearch.importData.aws.models;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName="rtd-Clients-Import")
public class TransactionItem {
    public TransactionItem(String document, String type, String dateImport){
        this.DocumentcustomerId = document;
        this.DocumentType = type;
        this.LastDateImport = dateImport;
    }
    public TransactionItem(){}

    private String DocumentcustomerId;


    private String DocumentType;


    private String LastDateImport;

    private String emailSended;

    @DynamoDBHashKey(attributeName = "DocumentcustomerId")
    public String getDocumentcustomerId() {
        return DocumentcustomerId;
    }
    @DynamoDBHashKey(attributeName = "DocumentcustomerId")
    public void setDocumentcustomerId(String documentcustomerId) {
        this.DocumentcustomerId = documentcustomerId;
    }

    @DynamoDBAttribute(attributeName="DocumentType")
    public String getDocumentType() {
        return DocumentType;
    }
    @DynamoDBAttribute(attributeName="DocumentType")
    public void setDocumentType(String documentType) {
        this.DocumentType = documentType;
    }

    @DynamoDBAttribute(attributeName="LastDateImport")
    public String getLastDateImport() {
        return LastDateImport;
    }
    @DynamoDBAttribute(attributeName="LastDateImport")
    public void setLastDateImport(String lastDateImport) {
        this.LastDateImport = lastDateImport;
    }
    @DynamoDBAttribute(attributeName="emailSended")
    public String getEmailSended() {
        return emailSended;
    }
    @DynamoDBAttribute(attributeName="emailSended")
    public void setEmailSended(String emailSended) {
        this.emailSended = emailSended;
    }
}
