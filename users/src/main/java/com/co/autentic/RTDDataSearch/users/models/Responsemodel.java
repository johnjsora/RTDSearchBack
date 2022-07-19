package com.co.autentic.RTDDataSearch.users.models;

public class Responsemodel {
    private int OperationCode;
    private String OperationMessage;

    public String getOperationMessage() {
        return OperationMessage;
    }

    public void setOperationMessage(String operationMessage) {
        OperationMessage = operationMessage;
    }

    public int getOperationCode() {
        return OperationCode;
    }

    public void setOperationCode(int operationCode) {
        OperationCode = operationCode;
    }
}
