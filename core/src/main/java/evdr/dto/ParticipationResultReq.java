package evdr.dto;

import java.util.List;

public class ParticipationResultReq {
    public String drId;
    public Integer cbl;
    public Integer consumed;
    public List<Item> items;

    public ParticipationResultReq(String drId, Integer cbl, Integer consumed, List<Item> items) {
        this.drId = drId;
        this.cbl = cbl;
        this.consumed = consumed;
        this.items = items;
    }

    public static class Item {
        public String resourceId;
        public String resourceIssuer;
        public Integer cbl;
        public Integer consumed;

        public Item(String resourceId, String resourceIssuer, Integer cbl, Integer consumed) {
            this.resourceId = resourceId;
            this.resourceIssuer = resourceIssuer;
            this.cbl = cbl;
            this.consumed = consumed;
        }
    }
}