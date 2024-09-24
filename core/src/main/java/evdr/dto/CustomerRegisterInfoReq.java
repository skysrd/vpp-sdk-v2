package evdr.dto;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class CustomerRegisterInfoReq {
    public List<Item> items;

    public CustomerRegisterInfoReq(List<Item> items) {
        this.items = items;
    }

    public static class Item {
        //고객번호
        public String customerNo;
        //등록구분
        public Short regDiv;
        //고객명
        public String name;
        //휴대폰번호
        public String mobile;
        public List<Resource> resource;

        public Item(String customerNo, Short regDiv, String name, String mobile, List<Resource> resource) {
            this.customerNo = customerNo;
            this.regDiv = regDiv;
            this.name = name;
            this.mobile = mobile;
            this.resource = resource;
        }

        public Item() {
        }
    }

    public static class Resource {
        //Resource ID
        public String resourceId;
        //Resource 발급자
        public String resourceIssuer;
        //가입일시
        public String regDate;
        //주소: 시도코드
        public String address1;
        //주소: 시군구코드
        public String address2;
        //주소: 동코드
        public String address3;
        //주소: 리코드
        public String address4;
        //주소: 상세
        public String address5;
        //계량기 구분
        public Short meterType;
        //계량기 번호
        public String meterNo;
        //한전계약종 (대분류)
        public String contractType1;
        //한전계약종 (상세)
        public String contractType2;
        //한전 계약전력
        public Short contractVolumn;
        //예비필드 미구현


        public Resource(String resourceId, String resourceIssuer, String regDate, String address1, String address2, String address3, String address4, String address5, Short meterType, String meterNo, String contractType1, String contractType2, Short contractVolumn) {
            this.resourceId = resourceId;
            this.resourceIssuer = resourceIssuer;
            this.regDate = regDate;
            this.address1 = address1;
            this.address2 = address2;
            this.address3 = address3;
            this.address4 = address4;
            this.address5 = address5;
            this.meterType = meterType;
            this.meterNo = meterNo;
            this.contractType1 = contractType1;
            this.contractType2 = contractType2;
            this.contractVolumn = contractVolumn;
        }

        public Resource() {
        }
    }
}