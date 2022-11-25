package pl.lottery.infrastructure.propertyconfigs.winningnumberservice;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import pl.lottery.infrastructure.winningnumberservice.WinningServiceConfigurable;

@Configuration
@ConfigurationProperties(prefix = "winning-numbers-service")
class WinningServicePropertyConfig implements WinningServiceConfigurable {

    private String protocol;
    private String serviceHost;
    private int servicePort;
    private String serviceApi;
    private String endPoint;

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

    public String getEndPointPath() {
        return endPoint;
    }

    public void setEndPointPath(String endPointPath) {
        this.endPoint = endPointPath;
    }

}
