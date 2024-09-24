package dr.dto;

public class DRServiceResponse {
    private String transactionId;
    private String code;

    public DRServiceResponse(String transactionId, String code) {
        this.transactionId = transactionId;
        this.code = code;
    }

    public DRServiceResponse() {

    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getCode() {
        return code;
    }
}
