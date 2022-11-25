package pl.lottery.infrastructure.winningnumberservice;

public interface WinningServiceConfigurable {

    String getProtocol();

    void setProtocol(String protocol);

    String getServiceHost();

    void setServiceHost(String serviceHost);

    int getServicePort();

    void setServicePort(int servicePort);

    String getServiceApi();

    void setServiceApi(String serviceApi);

    String getEndPointPath();

    void setEndPointPath(String endPointPath);

}
