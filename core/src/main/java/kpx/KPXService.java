package kpx;

import auth.TokenManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import common.ClientInfo;
import common.ServiceProperties;
import common.ServiceType;
import evdr.dto.DRServiceResponse;
import kpx.dto.CreateDrGroupInfoRequest;
import kpx.dto.CreateDrGroupRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
public class KPXService {
    private final String BASE_SERVER_ADDRESS;
    private final TokenManager tokenManager;
    private final ServiceProperties serviceProperties = ServiceProperties.getInstance();
    private final ClientInfo clientInfo;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final WebClient webClient;

    public KPXService(ServiceType serviceType, ClientInfo clientInfo) throws Exception {
        this.BASE_SERVER_ADDRESS = serviceProperties.getServerAddress(serviceType);
        this.tokenManager = new TokenManager(serviceType, clientInfo);
        this.clientInfo = clientInfo;
        this.webClient = WebClient.builder().baseUrl(BASE_SERVER_ADDRESS)
                .defaultHeader("Authorization", tokenManager.getToken().getAccessToken())
                .defaultHeader("cpo-id", ServiceProperties.getInstance().getCpoId())
                .build();
    }

    public DRServiceResponse createDrGroup(CreateDrGroupRequest createDrGroupRequest) throws Exception {
        return webClient.post()
                .uri(serviceProperties.getKPXServicePath("drGroup"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(createDrGroupRequest)
                .retrieve()
                .bodyToMono(DRServiceResponse.class)
                .block();
    }

    public DRServiceResponse createDrGroupInfo(CreateDrGroupInfoRequest createDrGroupInfoRequest) throws Exception {
        return webClient.post()
                .uri(serviceProperties.getKPXServicePath("drGroupInfo"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(createDrGroupInfoRequest)
                .retrieve()
                .bodyToMono(DRServiceResponse.class)
                .block();
    }
}
