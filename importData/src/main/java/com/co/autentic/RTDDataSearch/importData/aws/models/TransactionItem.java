package com.co.autentic.RTDDataSearch.importData.aws.models;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName="rtd-Clients-New-Import")
public class TransactionItem {
    public TransactionItem(String document, String Entity, String dateImport){
        this.DocumentcustomerId = document;
        this.Entity = Entity;
        this.LastDateImport = dateImport;
    }
    public TransactionItem(){}

    private String DocumentcustomerId;


    private String Entity;


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

    @DynamoDBRangeKey(attributeName="Entity")
    public String getEntity() {
        return Entity;
    }
    @DynamoDBRangeKey(attributeName="Entity")
    public void setEntity(String Entity) {
        this.Entity = Entity;
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
