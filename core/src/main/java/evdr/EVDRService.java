package evdr;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import common.ClientInfo;
import common.ServiceProperties;
import common.ServiceType;
import evdr.dto.BulkPowerConsumptionDataReq;
import evdr.dto.BulkPowerConsumptionDataResponse;
import evdr.dto.CustomerRegisterInfoReq;
import evdr.dto.CustomerRegisterInfoResponse;
import evdr.dto.DRServiceResponse;
import evdr.dto.ParticipationRequest;
import evdr.dto.ParticipationResultResponse;
import evdr.dto.ParticipationResultReq;
import evdr.dto.PowerConsumptionDataReq;
import evdr.dto.UserAgreementFileReq;
import evdr.dto.UserAgreementFileResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;
import util.WebClientUtil;

import java.io.IOException;

@Slf4j
public class EVDRService {
    private final ServiceProperties serviceProperties = ServiceProperties.getInstance();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final WebClientUtil webClientUtil;

    public EVDRService(ServiceType serviceType, ClientInfo clientInfo) throws Exception {
        this.webClientUtil = new WebClientUtil(serviceType, clientInfo);
    }

    //전력 사용량 벌크 전송
    public BulkPowerConsumptionDataResponse bulkPowerConsumptionData(BulkPowerConsumptionDataReq req) throws Exception
    {
        log.info("[START] bulkPowerConsumptionData ");

        String responseString = webClientUtil.postForString(serviceProperties.getDRServicePath("bulkPowerConsumptionData"), req);

        BulkPowerConsumptionDataResponse serviceResponse = webClientUtil.typeCast(responseString, BulkPowerConsumptionDataResponse.class);

        return serviceResponse;
    }

    //전력사용량 전송
    public DRServiceResponse powerConsumptionData(PowerConsumptionDataReq req) throws Exception
    {
        log.info("[START] powerConsumptionData ");

        String responseString = webClientUtil.postForString(serviceProperties.getDRServicePath("powerConsumptionData"), req);

        DRServiceResponse serviceResponse = webClientUtil.typeCast(responseString, DRServiceResponse.class);

        return serviceResponse;
    }

    //고객 가입 정보 전송
    public CustomerRegisterInfoResponse customerRegisterInfo(CustomerRegisterInfoReq req) throws Exception {
        log.info("[START] customerRegisterInfo ");

        String responseString = webClientUtil.postForString(serviceProperties.getDRServicePath("customerRegisterInfo"), req);

        CustomerRegisterInfoResponse customerRegisterInfoResponse = webClientUtil.typeCast(responseString, CustomerRegisterInfoResponse.class);

        return customerRegisterInfoResponse;
    }
//
//    public DRServiceResponse customerUnregisterInfo(ClientInfo clientInfo, CustomerUnregisterReq req) throws Exception
//    {
//        log.info("[START] customerUnregisterInfo ");
//        Map<String, String> headerInfo = initHeaderInfo(clientInfo);
//        URIBuilder uriBuilder = new URIBuilder(BASE_SERVER_ADDRESS)
//                .setPath(serviceProperties.getDRServicePath("customerUnregisterInfo"));
//
//        log.info("[INFO] uriBuilder : " + uriBuilder.toString());
//        return new CustomerUnregisterResponse(
//                getResultFromResMessage(
//                        new HttpUtil(false).
//                                sendPost(uriBuilder.build().toString(),
//                                        headerInfo,
//                                        getBodyFromRequest(req))));
//    }
//
//    public DRServiceResponse participationResult(ParticipationResultReq req) throws Exception
//    {
//        // TODO Auto-generated method stub
//        return null;
//    }


    //사용자 등록동의서 전송
    public DRServiceResponse userAgreementFile(UserAgreementFileReq req) throws Exception
    {
        log.info("[START] userAgreementFile ");

        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        String fileInfoJson = "{\"fileInfos\":" + objectMapper.writeValueAsString(req.getFileInfos()) + "}";
        builder.part("fileInfos", fileInfoJson, MediaType.APPLICATION_JSON);

        // MultipartFile을 처리하여 multipart/form-data에 추가
        for (MultipartFile file : req.getAgreementFiles()) {
            try {
                builder.part("agreementFiles", new InputStreamResource(file.getInputStream()))
                        .header("Content-Disposition", "form-data; name=agreementFiles; filename=" + file.getOriginalFilename());
            } catch (IOException e) {
                throw new RuntimeException("파일 처리 중 오류 발생", e);
            }
        }

        log.info(builder.toString());

        Mono<UserAgreementFileResponse> response = webClientUtil.getWebClient().post()
                .uri(serviceProperties.getDRServicePath("userAgreementFile"))
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .bodyValue(builder.build())
                .retrieve()
                .bodyToMono(String.class)
                .map(res -> {
                    try {
                        return objectMapper.readValue(res, UserAgreementFileResponse.class);
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                        return null;
                    }
                })
                .doOnError(e -> log.error("Error: ", e));

        return response.block();
    }

    //DR 결과 정보 전송
    public ParticipationResultResponse participationResult(ParticipationResultReq req) throws Exception
    {
        log.info("[START] participationResult ");

        String responseString = webClientUtil.postForString(serviceProperties.getDRServicePath("participationResult"), req);

        ParticipationResultResponse serviceResponse = webClientUtil.typeCast(responseString, ParticipationResultResponse.class);

        return serviceResponse;
    }

    public String participation(ParticipationRequest req) throws Exception {
        log.info("[START] participation ");

        String responseString = webClientUtil.postForString(serviceProperties.getDRServicePath("participation"), req);

        return responseString;

    }

//    public DRServiceResponse bulkPowerConsumptionData;
}
