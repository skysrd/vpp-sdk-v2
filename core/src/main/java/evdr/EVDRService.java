package evdr;

import auth.Token;
import auth.TokenManager;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import common.ClientInfo;
import common.ServiceProperties;
import evdr.dto.BulkPowerConsumptionDataReq;
import evdr.dto.CustomerRegisterInfoReq;
import evdr.dto.CustomerRegisterInfoResponse;
import evdr.dto.DRServiceResponse;
import evdr.dto.PowerConsumptionDataReq;
import evdr.dto.UserAgreementFileReq;
import evdr.dto.UserAgreementFileResponse;
import common.ServiceType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;

@Slf4j
public class EVDRService {

    private final String BASE_SERVER_ADDRESS;
    private final TokenManager tokenManager;
    private final ServiceProperties serviceProperties = ServiceProperties.getInstance();
    private final ClientInfo clientInfo;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final WebClient webClient;

    public EVDRService(ServiceType serviceType, ClientInfo clientInfo) throws Exception {
        this.BASE_SERVER_ADDRESS = serviceProperties.getServerAddress(serviceType);
        this.tokenManager = new TokenManager(serviceType, clientInfo);
        this.clientInfo = clientInfo;
        this.webClient = WebClient.builder().baseUrl(BASE_SERVER_ADDRESS)
                .defaultHeader("Authorization", getToken().getAccessToken())
                .defaultHeader("cpo-id", "005")
                .build();
    }

    //TODO: 각 기능 별 Log 작성

    public DRServiceResponse bulkPowerConsumptionData(BulkPowerConsumptionDataReq req) throws Exception
    {
        log.info("[START] bulkPowerConsumptionData ");

        DRServiceResponse response = webClient.post()
                .uri(serviceProperties.getDRServicePath("bulkPowerConsumptionData"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(req)
                .retrieve()
                .bodyToMono(String.class)
                .map(res -> {
                    try {
                        return objectMapper.readValue(res, DRServiceResponse.class);
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                        return null;
                    }
                })
                .block();

        return response;
    }

    public DRServiceResponse powerConsumptionData(PowerConsumptionDataReq req) throws Exception
    {
        log.info("[START] powerConsumptionData ");
        DRServiceResponse serviceResponse = webClient.post()
                .uri(serviceProperties.getDRServicePath("powerConsumptionData"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(req)
                .retrieve()
                .bodyToMono(String.class)
                .map(res -> {
                    try {
                        return objectMapper.readValue(res, DRServiceResponse.class);
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                        return null;
                    }
                })
                .block();

        return serviceResponse;
    }

    public CustomerRegisterInfoResponse customerRegisterInfo(CustomerRegisterInfoReq req) throws Exception {
        log.info("[START] customerRegisterInfo ");

        CustomerRegisterInfoResponse response = webClient.post()
                .uri(serviceProperties.getDRServicePath("customerRegisterInfo"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(req)
                .retrieve()
                .bodyToMono(String.class)
                .map(res -> {
                    try {
                        return objectMapper.readValue(res, CustomerRegisterInfoResponse.class);
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                        return null;
                    }
                })
                .block();

        return response;
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

        Mono<UserAgreementFileResponse> response = webClient.post()
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

    public Token getToken() throws Exception {
        return tokenManager.getToken();
    }

//    public DRServiceResponse bulkPowerConsumptionData;
}
