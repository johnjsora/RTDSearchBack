package com.co.autentic.RTDDataSearch.users.aws.models;


import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName="rtd-Clients-detail")
public class usermodel {
    public usermodel(String email, String userName, String userIdentification, String identificationType){
        this.email = email;
        this.identificationType = identificationType;
        this.userName = userName;
        this.userIdentification = userIdentification;

    }
    public usermodel(){}

    private String email;

    private String userName;

    private String userIdentification;

    private String identificationType;

    private String role;

    @DynamoDBHashKey(attributeName = "email")
    public String getEmail() {
        return email;
    }
    @DynamoDBHashKey(attributeName = "email")
    public void setEmail(String email) {
        this.email = email;
    }

    @DynamoDBAttribute(attributeName="userName")
    public String getUserName() {
        return userName;
    }
    @DynamoDBAttribute(attributeName="userName")
    public void setUserName(String userName) {
        this.userName = userName;
    }

    @DynamoDBAttribute(attributeName="userIdentification")
    public String getUserIdentification() {
        return userIdentification;
    }
    @DynamoDBAttribute(attributeName="userIdentification")
    public void setUserIdentification(String userIdentification) {
        this.userIdentification = userIdentification;
    }

    @DynamoDBAttribute(attributeName="identificationType")
    public String getIdentificationType() {
        return identificationType;
    }
    @DynamoDBAttribute(attributeName="identificationType")
    public void setIdentificationType(String identificationType) {
        this.identificationType = identificationType;
    }

    @DynamoDBAttribute(attributeName="role")
    public String getRole() {
        return role;
    }
    @DynamoDBAttribute(attributeName="role")
    public void setRole(String role) {
        this.role = role;
    }
}
