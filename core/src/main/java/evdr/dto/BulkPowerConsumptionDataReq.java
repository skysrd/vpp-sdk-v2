package evdr.dto;

import java.util.List;

public class BulkPowerConsumptionDataReq {
    public List<Item> items;

    public static class Item {
        public String resourceId;
        public String resourceIssuer;
        public Short meterType;
        public String meterNo;
        public String date;
        public List<List<Object>> data;
    }
}