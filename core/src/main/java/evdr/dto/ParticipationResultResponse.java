package evdr.dto;

import java.util.List;

public class ParticipationResultResponse extends DRServiceResponse {
    public Result result;

    public static class Result {
        public String code;
        public String transactionId;
        public List<Item> item;

        public static class Item {
            public String resourceId;
            public String code;
            public String description;
            public String resourceIssuer;
        }
    }
}