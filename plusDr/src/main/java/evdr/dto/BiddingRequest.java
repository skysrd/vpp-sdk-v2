package evdr.dto;

import java.util.List;

public class BiddingRequest {
    public int drType;
    public int drArea;
    public List<Item> items;

    public static class Item {
        public String bidId;
        public String drStartTime;
        public String drEndTime;
        public int capacity;
        public String resourceIssuer;
        public List<String> resourceId;
    }
}
