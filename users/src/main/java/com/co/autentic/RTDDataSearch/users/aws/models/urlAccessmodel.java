package com.co.autentic.RTDDataSearch.users.aws.models;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName="urlAccess99")
public class urlAccessmodel {
    private String caseNumber;
    private String userDoc;
    private String gsi_userDoc;

    @DynamoDBHashKey(attributeName = "caseNumber")
    public String getCaseNumber() {
        return caseNumber;
    }
    @DynamoDBHashKey(attributeName = "caseNumber")
    public void setCaseNumber(String caseNumber) {
        this.caseNumber = caseNumber;
    }
    @DynamoDBRangeKey(attributeName="userDoc")
    public String getUserDoc() {
        return userDoc;
    }
    @DynamoDBRangeKey(attributeName="userDoc")
    public void setUserDoc(String userDoc) {
        this.userDoc = userDoc;
    }

    public String getGsi_userDoc() {
        return gsi_userDoc;
    }

    public void setGsi_userDoc(String gsi_userDoc) {
        this.gsi_userDoc = gsi_userDoc;
    }
}
