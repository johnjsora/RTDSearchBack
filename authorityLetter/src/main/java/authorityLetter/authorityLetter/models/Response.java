package authorityLetter.authorityLetter.models;

public class Response {
    private int operationCode;
    private String operationMsg;

    public int getOperationCode() {
        return operationCode;
    }

    public void setOperationCode(int operationCode) {
        this.operationCode = operationCode;
    }

    public String getOperationMsg() {
        return operationMsg;
    }

    public void setOperationMsg(String operationMsg) {
        this.operationMsg = operationMsg;
    }
}
