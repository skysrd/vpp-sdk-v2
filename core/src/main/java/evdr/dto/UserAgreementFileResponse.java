package evdr.dto;

import java.util.List;

public class UserAgreementFileResponse extends DRServiceResponse {
    public Result result;
    public List<String> uploadedFileInfos;

    public class Result {
        public String code;
        public String transactionId;
        public String description;

        public Result() {
        }

        public Result(String transactionId, String code, String description) {
            this.transactionId = transactionId;
            this.code = code;
            this.description = description;
        }
    }
}