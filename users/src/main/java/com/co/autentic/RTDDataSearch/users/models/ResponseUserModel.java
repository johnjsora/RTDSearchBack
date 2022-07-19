package com.co.autentic.RTDDataSearch.users.models;

import com.co.autentic.RTDDataSearch.users.aws.models.usermodel;
import com.co.autentic.RTDDataSearch.users.services.users;

public class ResponseUserModel extends Responsemodel {
    private usermodel operationContent;

    public usermodel getOperationContent() {
        return operationContent;
    }

    public void setOperationContent(usermodel operationContent) {
        this.operationContent = operationContent;
    }
}
