package com.co.autentic.RTDDataSearch.importData.models;

import java.util.Date;

public class s3FileInformation {
    private byte[] file;
    private Date lastModifiedDate;

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }
}
