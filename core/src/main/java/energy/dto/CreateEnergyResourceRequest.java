package energy.dto;

import java.util.List;

public class CreateEnergyResourceRequest {
    public String rid;
    public String rid_issuer;
    public String name;
    public String user_name;
    public String user_mobile;
    public String user_email;
    public String address_1;
    public String address_2;
    public String address_3;
    public String address_4;
    public String address_5;
    public String address_text;
    public String er_type;
    public String cpo_id;
    public String cpo_customer_no;
    public String regist_date;
    public String user_type;
    public List<Item> items;

    public static class Item {
        public String meter_no;
        public String remark_1;
        public String remark_2;
        public String meter_type;
    }
}
