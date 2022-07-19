package com.co.autentic.RTDDataSearch.users.models;

public class AttachedFileModel {
    public AttachedFileModel(byte[] file, String fileName){
        this.fileContent = file;
        this.fileName = fileName;
    }
    public AttachedFileModel(){}
    private byte[] fileContent;
    private String fileName;

    public byte[] getFileContent() {
        return fileContent;
    }

    public void setFileContent(byte[] fileContent) {
        this.fileContent = fileContent;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
