package evdr.dto;

import java.util.List;

public class CustomerUnregisterResponse extends DRServiceResponse {

    public Result result;

    public static class Result {
        public String code;
        public List<Item> items;
        public String transactionId;

            private static class Item {
                public String code;
                public String customerNo;
                public String resourceId;
                public String description;
            }

    }
}
