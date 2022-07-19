package com.co.autentic.RTDDataSearch.users.models;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;

import java.util.Map;

public class UserExistsRequestModel {
    //User Exist Data
    private String userId="N/A";
    private String typeDocument="N/A";

    //Proposal Data
    private boolean proposal = false;
    private String sendBy="N/A";
    private String reference="N/A";
    private double amount=0;
    private String sendByEmail ="N/A";
    private String typeProposal="N/A";
    private String Description="N/A";
    private String Bank="N/A";

    //Files
    private String fileBase64="N/A";
    private String fileName="N/A";
    private String fileExtention="N/A";

    //GetAll
    private String dateIni;
    private String dateEnd;

    //LastEval
    private Map<String, AttributeValue> lastKeyEvaluated;
    private int limitScann = 10;

    //Getter and Setter

    public boolean isProposal() {
        return proposal;
    }

    public void setProposal(boolean proposal) {
        this.proposal = proposal;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTypeDocument() {
        return typeDocument;
    }

    public void setTypeDocument(String typeDocument) {
        this.typeDocument = typeDocument;
    }

    public String getSendBy() {
        return sendBy;
    }

    public void setSendBy(String sendBy) {
        this.sendBy = sendBy;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getSendByEmail() {return sendByEmail;}

    public void setSendByEmail(String sendByEmail) {this.sendByEmail = sendByEmail;}

    public String getTypeProposal() {
        return typeProposal;
    }

    public void setTypeProposal(String typeProposal) {
        this.typeProposal = typeProposal;
    }

    public String getFileBase64() {
        return fileBase64;
    }

    public void setFileBase64(String fileBase64) {
        this.fileBase64 = fileBase64;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileExtention() {
        return fileExtention;
    }

    public void setFileExtention(String fileExtention) {
        this.fileExtention = fileExtention;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getBank() {
        return Bank;
    }

    public void setBank(String bank) {
        Bank = bank;
    }

    public String getDateIni() {
        return dateIni;
    }

    public void setDateIni(String dateIni) {
        this.dateIni = dateIni;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }

    public Map<String, AttributeValue> getLastKeyEvaluated() {
        return lastKeyEvaluated;
    }

    public void setLastKeyEvaluated(Map<String, AttributeValue> lastKeyEvaluated) {
        this.lastKeyEvaluated = lastKeyEvaluated;
    }

    public int getLimitScann() {
        return limitScann;
    }

    public void setLimitScann(int limitScann) {
        this.limitScann = limitScann;
    }
}
