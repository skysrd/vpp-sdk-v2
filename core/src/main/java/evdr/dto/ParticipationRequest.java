package evdr.dto;

import java.util.List;

public class ParticipationRequest {
    public List<Item> items;

    public static class Item {
        public String erid;
        public String cpo_id;
        public String participation;
    }
}