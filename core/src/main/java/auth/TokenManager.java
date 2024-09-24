package auth;

import common.ClientInfo;
import common.ServiceProperties;
import dr.enums.ServiceType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Slf4j
public class TokenManager implements Runnable {
    public Token token;
    private final String BASE_SERVER_ADDRESS;
    private final ClientInfo clientInfo;
    private final WebClient webClient;

    private final ExecutorService executorService;
    private final Future<?> futureTask;

    public TokenManager(ServiceType serviceType, ClientInfo clientInfo) {
        BASE_SERVER_ADDRESS = ServiceProperties.getInstance().getServerAddress(serviceType);
        log.info("Base Server Address is : " + BASE_SERVER_ADDRESS);
        this.clientInfo = clientInfo;
        this.executorService = Executors.newSingleThreadExecutor();
        this.webClient = WebClient.create(BASE_SERVER_ADDRESS);
        this.futureTask = executorService.submit(this::run);
        log.info("[INFO] TokenManager initialized");
    }

    public Token getToken() throws Exception {
        log.info("[INFO] Getting token");
        if (token == null || token.isExpired()) {
            refreshToken();
        }
        log.info("[INFO] Token: " + token.getAccessToken());
        return token;
    }

    private void refreshToken() throws Exception {
        log.info("[INFO] Refreshing token");

        TokenDto tokenDto = webClient.post()
                .uri("/api/1.0/auth/token")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(clientInfo)
                .retrieve()
                .bodyToMono(TokenDto.class)
                .block();

        token = tokenDto.getToken();

        log.info("[INFO] Token refreshed: " + token.getAccessToken());
    }

    @Override
    public void run() {
        log.info("[INFO] TokenManager started");
        while (!Thread.currentThread().isInterrupted()) {
            try {
                getToken();
                // Sleep for 1 hour before refreshing the token again
                Thread.sleep(60 * 60 * 1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void stop() {
        log.info("[INFO] TokenManager stopped");
        futureTask.cancel(true);
        executorService.shutdown();
    }
}
