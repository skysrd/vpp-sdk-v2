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
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

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

    public DRServiceResponse createEnergyGroup(CreateEnergyGroupRequest createEnergyGroupRequest) throws Exception {
        return webClient.post()
                .uri(serviceProperties.getEnergyServicePath("createEnergyGroup"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(createEnergyGroupRequest)
                .retrieve()
                .bodyToMono(DRServiceResponse.class)
                .block();
    }

    public DRServiceResponse createEnergyResource(CreateEnergyResourceRequest createEnergyResourceRequest) throws Exception {
        return webClient.post()
                .uri(serviceProperties.getEnergyServicePath("createEnergyResource"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(createEnergyResourceRequest)
                .retrieve()
                .bodyToMono(String.class)
                .map(response -> {
                    try {
                        return objectMapper.readValue(response, DRServiceResponse.class);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                })
                .block();
    }
}
