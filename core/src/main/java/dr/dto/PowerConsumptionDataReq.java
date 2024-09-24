package dr.dto;

import java.util.List;

public class PowerConsumptionDataReq
{
        public List<Item> items;

        public PowerConsumptionDataReq(List<Item> items) {
                this.items = items;
        }

        public static class Item {
                //Resource ID
                public String resourceId;
                //Resource 발급자
                public String resourceIssuer;
                //계량기 구분
                public Short meterType;
                //계량기 번호
                public String meterNo;
                //전력소비시간
                public String date;
                //전력소비량
                public Integer data;

                public Item(String resourceId, String resourceIssuer, Short meterType, String meterNo, String date, Integer data) {
                        this.resourceId = resourceId;
                        this.resourceIssuer = resourceIssuer;
                        this.meterType = meterType;
                        this.meterNo = meterNo;
                        this.date = date;
                        this.data = data;
                }
        }
}