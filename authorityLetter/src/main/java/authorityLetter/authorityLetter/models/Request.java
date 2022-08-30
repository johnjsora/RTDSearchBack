package authorityLetter.authorityLetter.models;

public class Request {
    private String baseFile;
    private String fileName="N/A";
    private String fileExtention="N/A";
    private String emailSended;

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

    public String getBaseFile() {
        return baseFile;
    }

    public void setBaseFile(String baseFile) {
        this.baseFile = baseFile;
    }

    public String getEmailSended() {
        return emailSended;
    }

    public void setEmailSended(String emailSended) {
        this.emailSended = emailSended;
    }
}
