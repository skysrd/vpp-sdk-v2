package common;

import lombok.Getter;

@Getter
public class ClientInfo {
    private final String client_id;
    private final String client_key;

    public ClientInfo(String client_id, String client_key)
    {
        this.client_id = client_id;
        this.client_key = client_key;
    }

    @Override
    public String toString()
    {
        return "ClientInfo.java [client_id=" + client_id + ", client_key=" + client_key + "]";
    }
}
