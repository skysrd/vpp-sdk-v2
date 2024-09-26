package util;

import auth.TokenManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import common.ClientInfo;
import common.ServiceProperties;
import common.ServiceType;
import org.springframework.web.reactive.function.client.WebClient;

public class WebClientUtil {
    private final WebClient webClient;

    private final String BASE_SERVER_ADDRESS;
    private final TokenManager tokenManager;
    private final ServiceProperties serviceProperties = ServiceProperties.getInstance();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public WebClientUtil(ServiceType serviceType, ClientInfo clientInfo) throws Exception {
        this.BASE_SERVER_ADDRESS = serviceProperties.getServerAddress(serviceType);
        this.tokenManager = new TokenManager(serviceType, clientInfo);
        this.webClient = WebClient.builder().baseUrl(BASE_SERVER_ADDRESS)
                .defaultHeader("Authorization", tokenManager.getToken().getAccessToken())
                .defaultHeader("cpo-id", "005")
                .build();
    }
    
    public WebClient getWebClient() throws Exception {
        return webClient;
    }

    public <T> T post(String path, Object request, Class<T> responseType) {
        return webClient.post()
                .uri(path)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(responseType)
                .block();
    }

    public String postForString(String path, Object request) throws Exception {
        return post(path, request, String.class);
    }

    public <T> T typeCast(String response, Class<T> responseType) throws Exception {
        return objectMapper.readValue(response, responseType);
    }

}
