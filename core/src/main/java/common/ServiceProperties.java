package common;

import dr.enums.ServiceType;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ServiceProperties {
    private static ServiceProperties instance;
    private Properties properties;

    private ServiceProperties() {
        properties = new Properties();
        try {
            InputStream input = new FileInputStream("src/main/resources/VppApiInfo.properties");
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ServiceProperties getInstance() {
        if (instance == null) {
            instance = new ServiceProperties();
        }
        return instance;
    }

    public String getServerAddress(ServiceType serviceType) {
        return properties.getProperty("service." + serviceType.name().toLowerCase() + ".api");
    }

    public String getDRServicePath(String serviceName) {
        return properties.getProperty("dr.service.path." + serviceName);
    }
}
