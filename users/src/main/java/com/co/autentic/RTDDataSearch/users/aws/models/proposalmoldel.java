package com.co.autentic.RTDDataSearch.users.aws.models;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

import java.util.Map;

@DynamoDBTable(tableName="rtd-Clients-Proposal")
public class proposalmoldel {
    private String idProp;
    private long dateInserted;
    private String userDocument ="N/A";
    private String userDocumentType="N/A";
    private String sendBy="N/A";
    private String sendByEmail="N/A";
    private String status="N/A";
    private String reference="N/A";
    private double amount =0;
    private String typeProposal="N/A";
    private String keyFile="N/A";
    private int usersCount =1;
    private boolean haveInexists = false;
    private String description ="N/A";
    private String bank ="N/A";
    Map<String, AttributeValue> lastKeyEvaluated = null;

    public proposalmoldel(String idProp, long dateInserted,String sendBy,String sendByEmail,String status, String reference,double amount,String typeProposal,String keyFile, int usersCount, boolean haveInexists, String description, String bank,
                          String userDocument, String userDocumentType){
        this.idProp = idProp;
        this.dateInserted = dateInserted;
        this.sendBy =sendBy;
        this.sendByEmail = sendByEmail;
        this.status = status;
        this.reference = reference;
        this.amount = amount;
        this.typeProposal = typeProposal;
        this.keyFile = keyFile;
        this.usersCount = usersCount;
        this.haveInexists= haveInexists;
        this.description = description;
        this.bank = bank;
        this.userDocument = userDocument;
        this.userDocumentType = userDocumentType;
    }
    public proposalmoldel(){}


    @DynamoDBHashKey(attributeName = "idProp")
    public String getIdProp() {
        return idProp;
    }
    @DynamoDBHashKey(attributeName = "idProp")
    public void setIdProp(String idProp) {
        this.idProp = idProp;
    }

    @DynamoDBAttribute(attributeName="dateInserted")
    public long getDateInserted() {
        return dateInserted;
    }
    @DynamoDBAttribute(attributeName="dateInserted")
    public void setDateInserted(long dateInserted) {
        this.dateInserted = dateInserted;
    }

    @DynamoDBAttribute(attributeName="sendBy")
    public String getSendBy() {
        return sendBy;
    }
    @DynamoDBAttribute(attributeName="sendBy")
    public void setSendBy(String sendBy) {
        this.sendBy = sendBy;
    }

    @DynamoDBAttribute(attributeName="sendByEmail")
    public String getSendByEmail() {
        return sendByEmail;
    }
    @DynamoDBAttribute(attributeName="sendByEmail")
    public void setSendByEmail(String sendByEmail) {
        this.sendByEmail = sendByEmail;
    }

    @DynamoDBAttribute(attributeName="status")
    public String getStatus() {
        return status;
    }
    @DynamoDBAttribute(attributeName="status")
    public void setStatus(String status) {
        this.status = status;
    }

    @DynamoDBAttribute(attributeName="amount")
    public double getAmount() {
        return amount;
    }
    @DynamoDBAttribute(attributeName="amount")
    public void setAmount(double amount) {
        this.amount = amount;
    }

    @DynamoDBAttribute(attributeName="typeProposal")
    public String getTypeProposal() {
        return typeProposal;
    }
    @DynamoDBAttribute(attributeName="typeProposal")
    public void setTypeProposal(String typeProposal) {
        this.typeProposal = typeProposal;
    }

    @DynamoDBAttribute(attributeName="keyFile")
    public String getKeyFile() {
        return keyFile;
    }
    @DynamoDBAttribute(attributeName="keyFile")
    public void setKeyFile(String keyFile) {
        this.keyFile = keyFile;
    }

    @DynamoDBAttribute(attributeName="reference")
    public String getReference() { return reference;}
    @DynamoDBAttribute(attributeName="reference")
    public void setReference(String reference) {this.reference = reference;}

    @DynamoDBAttribute(attributeName="usersCount")
    public int getUsersCount() {
        return usersCount;
    }
    @DynamoDBAttribute(attributeName="usersCount")
    public void setUsersCount(int usersCount) {
        this.usersCount = usersCount;
    }

    @DynamoDBTyped(DynamoDBMapperFieldModel.DynamoDBAttributeType.BOOL)
    @DynamoDBAttribute(attributeName="haveInexists")
    public boolean isHaveInexists() {
        return haveInexists;
    }
    @DynamoDBTyped(DynamoDBMapperFieldModel.DynamoDBAttributeType.BOOL)
    @DynamoDBAttribute(attributeName="haveInexists")
    public void setHaveInexists(boolean haveInexists) {
        this.haveInexists = haveInexists;
    }

    @DynamoDBAttribute(attributeName="description")
    public String getDescription() {
        return description;
    }
    @DynamoDBAttribute(attributeName="description")
    public void setDescription(String description) {
        this.description = description;
    }

    @DynamoDBAttribute(attributeName="bank")
    public String getBank() {
        return bank;
    }
    @DynamoDBAttribute(attributeName="bank")
    public void setBank(String bank) {
        this.bank = bank;
    }

    @DynamoDBAttribute(attributeName="userDocument")
    public String getUserDocument() {
        return userDocument;
    }
    @DynamoDBAttribute(attributeName="userDocument")
    public void setUserDocument(String userDocument) {
        this.userDocument = userDocument;
    }

    @DynamoDBAttribute(attributeName="userDocumentType")
    public String getUserDocumentType() {
        return userDocumentType;
    }
    @DynamoDBAttribute(attributeName="userDocumentType")
    public void setUserDocumentType(String userDocumentType) {
        this.userDocumentType = userDocumentType;
    }

    public Map<String, AttributeValue> getLastKeyEvaluated() {
        return lastKeyEvaluated;
    }
    public void setLastKeyEvaluated(Map<String, AttributeValue> lastKeyEvaluated) {
        this.lastKeyEvaluated = lastKeyEvaluated;
    }
}
