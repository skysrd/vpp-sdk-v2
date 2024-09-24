package dr.dto;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;
import java.util.Map;

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