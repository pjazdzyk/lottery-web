package com.synerset.lottery.infrastructure.propertyconfigs.winningnumberservice;

import com.synerset.lottery.infrastructure.winningnumberservice.WinningServiceConfigurable;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "winning-numbers-service")
class WinningServicePropertyConfig implements WinningServiceConfigurable {

    private String protocol;
    private String baseUrl;
    private String serviceApi;
    private String endPoint;

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
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

    @Override
    public String getBaseUrl() {
        return this.baseUrl;
    }

    @Override
    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

}
