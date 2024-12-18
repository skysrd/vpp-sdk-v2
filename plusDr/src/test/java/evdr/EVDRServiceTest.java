package evdr;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import common.ClientInfo;
import common.ServiceType;
import evdr.dto.BiddingDeleteRequest;
import evdr.dto.BiddingDeleteResponse;
import evdr.dto.BiddingRequest;
import evdr.dto.BiddingResponse;
import evdr.dto.BulkPowerConsumptionDataReq;
import evdr.dto.BulkPowerConsumptionDataResponse;
import evdr.dto.DRServiceResponse;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class EVDRServiceTest {
    public ClientInfo clientInfo = new ClientInfo("herit", "ffe0356c-10c2-409c-8342-6d5328b90968");
    public EVDRService evdrService = new EVDRService(ServiceType.TB, clientInfo);
    private final ObjectMapper objectMapper = new ObjectMapper();

    EVDRServiceTest() throws Exception {
    }

    @Test
    void bidding() throws Exception {
        //given
        String jsonString = "{\"drType\": 2, \"drArea\": 1, \"items\": [{\"bidId\": \"test001\", \"drStartTime\": \"202410311400\", \"drEndTime\": \"202410311500\", \"capacity\": 200, \"resourceIssuer\": \"goldtroops\", \"resourceId\": [\"023456785\"]}]}";

        BiddingRequest req = objectMapper.readValue(jsonString, BiddingRequest.class);

        //when
        BiddingResponse response = evdrService.bidding(req);
        System.out.println("Response : " + objectMapper.writeValueAsString(response));

        //then
        assertThat(response).isNotNull();
        assertThat(response.result.code).isEqualTo("0");
    }

    @Test
    void biddingDelete() throws Exception {
        //given
        String jsonString = "{\"bidId\":[\"plusDRTest3\"]}";
        BiddingDeleteRequest request = objectMapper.readValue(jsonString, BiddingDeleteRequest.class);

        //when
        BiddingDeleteResponse response = evdrService.biddingDelete(request);
        System.out.println("Response : " + objectMapper.writeValueAsString(response));

        //then
        assertThat(response).isNotNull();
        assertThat(response.result.code).isEqualTo("0");
    }
}