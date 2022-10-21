package pl.lottery.infrastructure.winningnumberservice;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "winning-numbers-service")
public class WinningServicePropertyConfig implements WinningServiceConfigurable {

    private String protocol;
    private String serviceHost;
    private int servicePort;
    private String serviceApi;
    private String generateEndpoint;
    private String retrieveEndpoint;
    private String deleteEndpoint;
    private String getAllEndpoint;

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getServiceHost() {
        return serviceHost;
    }

    public void setServiceHost(String serviceHost) {
        this.serviceHost = serviceHost;
    }

    public int getServicePort() {
        return servicePort;
    }

    public void setServicePort(int servicePort) {
        this.servicePort = servicePort;
    }

    public String getServiceApi() {
        return serviceApi;
    }

    public void setServiceApi(String serviceApi) {
        this.serviceApi = serviceApi;
    }

    public String getGenerateEndpoint() {
        return generateEndpoint;
    }

    public void setGenerateEndpoint(String generateEndpoint) {
        this.generateEndpoint = generateEndpoint;
    }

    public String getRetrieveEndpoint() {
        return retrieveEndpoint;
    }

    public void setRetrieveEndpoint(String retrieveEndpoint) {
        this.retrieveEndpoint = retrieveEndpoint;
    }

    public String getDeleteEndpoint() {
        return deleteEndpoint;
    }

    public void setDeleteEndpoint(String deleteEndpoint) {
        this.deleteEndpoint = deleteEndpoint;
    }

    public String getGetAllEndpoint() {
        return getAllEndpoint;
    }

    public void setGetAllEndpoint(String getAllEndpoint) {
        this.getAllEndpoint = getAllEndpoint;
    }
}
