package com.synerset.lottery.infrastructure.winningnumberservice;

public interface WinningServiceConfigurable {

    String getProtocol();

    void setProtocol(String protocol);

    String getServiceApi();

    void setServiceApi(String serviceApi);

    String getEndPointPath();

    void setEndPointPath(String endPointPath);

    String getBaseUrl();

    void setBaseUrl(String baseUrl);
}
