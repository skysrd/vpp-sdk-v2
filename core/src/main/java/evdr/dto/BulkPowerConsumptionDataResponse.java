package evdr.dto;

public class BulkPowerConsumptionDataResponse extends DRServiceResponse {
    public Result result;

    public static class Result {
        public String code;
        public String description;
        public String transactionId;
    }
}