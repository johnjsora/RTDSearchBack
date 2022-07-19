package com.co.autentic.RTDDataSearch.users.models;

public class UserExistsResponse extends Responsemodel {
    private boolean exists = false;

    public boolean isExists() {
        return exists;
    }

    public void setExists(boolean exists) {
        this.exists = exists;
    }
}
