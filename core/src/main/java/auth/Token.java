package auth;

public class Token {

    public Token(String access_token, String refresh_token, String refresh_expires_in, String expires_in) {
        this.access_token = access_token;
        this.refresh_token = refresh_token;
        this.refresh_expires_in = refresh_expires_in;
        this.expires_in = expires_in;
    }

    private String access_token;
    private String refresh_token;
    private String refresh_expires_in;
    private String expires_in;

    public String getAccessToken() {
        return this.access_token;
    }

    public String getRefreshToken() {
        return this.refresh_token;
    }

    public boolean isExpired() {
        return Long.parseLong(expires_in) < System.currentTimeMillis();
    }

    public boolean isRefreshExpired() {
        return Long.parseLong(refresh_expires_in) < System.currentTimeMillis();
    }
}
