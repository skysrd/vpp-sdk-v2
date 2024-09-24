package evdr.dto;

import java.util.List;

public class CustomerUnregisterReq {
    public List<Item> items;

    public static class Item{
        public String customerNo;
    }
}
