package auth;

import common.ClientInfo;
import dr.enums.ServiceType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TokenManagerTest {

    @Test
    void testGetToken() throws Exception {
        TokenManager tokenManager = new TokenManager(ServiceType.TB, new ClientInfo("herit", "ffe0356c-10c2-409c-8342-6d5328b90968"));
        Token token = tokenManager.getToken();
        assertNotNull(token);
        System.out.println("Token: " + token.getAccessToken());
    }
}