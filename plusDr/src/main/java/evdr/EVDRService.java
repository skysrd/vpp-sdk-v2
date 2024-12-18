package evdr;

import com.fasterxml.jackson.databind.ObjectMapper;
import common.ClientInfo;
import common.ServiceProperties;
import common.ServiceType;
import evdr.dto.BiddingDeleteRequest;
import evdr.dto.BiddingDeleteResponse;
import evdr.dto.BiddingRequest;
import evdr.dto.BiddingResponse;
import lombok.extern.slf4j.Slf4j;
import util.WebClientUtil;

@Slf4j
public class EVDRService {
    private final ServiceProperties serviceProperties = ServiceProperties.getInstance();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final WebClientUtil webClientUtil;

    public EVDRService(ServiceType serviceType, ClientInfo clientInfo) throws Exception {
        this.webClientUtil = new WebClientUtil(serviceType, clientInfo);
    }

    public BiddingResponse bidding(BiddingRequest req) throws Exception
    {
        log.info("[START] bidding ");

        String responseString = webClientUtil.postForString(serviceProperties.getDRServicePath("bidding"), req);

        BiddingResponse serviceResponse = webClientUtil.typeCast(responseString, BiddingResponse.class);

        return serviceResponse;
    }

    public BiddingDeleteResponse biddingDelete(BiddingDeleteRequest req) throws Exception
    {
        log.info("[START] biddingDelete ");

        String responseString = webClientUtil.postForString(serviceProperties.getDRServicePath("biddingDelete"), req);

        BiddingDeleteResponse serviceResponse = webClientUtil.typeCast(responseString, BiddingDeleteResponse.class);

        return serviceResponse;
    }
}
