package energy;


import auth.TokenManager;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import common.ClientInfo;
import common.ServiceProperties;
import common.ServiceType;
import energy.dto.CreateEnergyGroupRequest;
import energy.dto.CreateEnergyResourceRequest;
import evdr.dto.DRServiceResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
public class EnergyService {
    private final String BASE_SERVER_ADDRESS;
    private final TokenManager tokenManager;
    private final ServiceProperties serviceProperties = ServiceProperties.getInstance();
    private final ClientInfo clientInfo;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final WebClient webClient;

    public EnergyService(ServiceType serviceType, ClientInfo clientInfo) throws Exception {
        this.BASE_SERVER_ADDRESS = serviceProperties.getServerAddress(serviceType);
        this.tokenManager = new TokenManager(serviceType, clientInfo);
        this.clientInfo = clientInfo;
        this.webClient = WebClient.builder().baseUrl(BASE_SERVER_ADDRESS)
                .defaultHeader("Authorization", tokenManager.getToken().getAccessToken())
                .defaultHeader("cpo-id", ServiceProperties.getInstance().getCpoId())
                .build();
    }

    //분산자원 등록
    public DRServiceResponse createEnergyResource(CreateEnergyResourceRequest createEnergyResourceRequest) throws Exception {
        String response = postStringBlock(serviceProperties.getEnergyServicePath("createEnergyResource"), MediaType.APPLICATION_JSON, createEnergyResourceRequest);

        DRServiceResponse serviceResponse = objectMapper.readValue(response, DRServiceResponse.class);

        return serviceResponse;
    }

    //Class Type 받아오는 block 요청
    private <T> T postBlock(String path, MediaType mediaType, Object body, Class<T> responseType) {
        return webClient.post()
                .uri(path)
                .contentType(mediaType)
                .bodyValue(body)
                .retrieve()
                .bodyToMono(responseType)
                .doOnError(e -> log.error("Error: ", e))
                .block();
    }

    //String Type 받아오는 block 요청
    //객체 내에 별도 객체가 있는 다차원 객체에 사용
    private String postStringBlock(String path, MediaType mediaType, Object body) {
        return postBlock(path, mediaType, body, String.class);
    }
}
