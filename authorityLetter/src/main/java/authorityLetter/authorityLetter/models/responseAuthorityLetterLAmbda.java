package authorityLetter.authorityLetter.models;

public class responseAuthorityLetterLAmbda {
    private int operationCode;
    private String operationMessage;
    private String documentBase;
    private String documentName;
    private String documentExtention;

    public int getOperationCode() {
        return operationCode;
    }

    public void setOperationCode(int operationCode) {
        this.operationCode = operationCode;
    }

    public String getOperationMessage() {
        return operationMessage;
    }

    public void setOperationMessage(String operationMessage) {
        this.operationMessage = operationMessage;
    }

    public String getDocumentBase() {
        return documentBase;
    }

    public void setDocumentBase(String documentBase) {
        this.documentBase = documentBase;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public String getDocumentExtention() {
        return documentExtention;
    }

    public void setDocumentExtention(String documentExtention) {
        this.documentExtention = documentExtention;
    }
}
