package dr.dto;

import java.util.List;

public class CustomerRegisterInfoResponse extends DRServiceResponse {
    public Result result;

    public static class Result {
        public String code;
        public List<ItemWrapper> items; // Changed from List<Item> to List<ItemWrapper>
        public String transactionId;
        public String description;

        public Result() {
        }
    }

    public static class ItemWrapper {
        public List<Item> item; // Changed from Item to List<Item>
    }

    public static class Item {
        public String code;
        public String kepcoCustomerNo;
        public String customerNo;
        public String description;
        public String errorCode;
        public String resourceId;
        public String resourceIssuer;

        public Item() {
        }
    }
}