package evdr.dto;

import java.util.List;

public class BiddingResponse extends DRServiceResponse{
    public Result result;

    public static class Result {
        public String code;
        public String transactionId;
        public List<Item> items;

        public static class Item {
            public String bidId;
            public String drStartTime;
            public String drEndTime;
            public String code;
            public String description;
        }
    }
}
