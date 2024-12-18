package evdr.dto;

import java.util.List;

public class BiddingDeleteResponse {
    public Result result;

    public static class Result {
        public String code;
        public String transactionId;
    }
}
