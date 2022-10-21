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

    String getGenerateEndpoint();

    void setGenerateEndpoint(String generateEndpoint);

    String getRetrieveEndpoint();

    void setRetrieveEndpoint(String retrieveEndpoint);

    String getDeleteEndpoint();

    void setDeleteEndpoint(String deleteEndpoint);

    String getGetAllEndpoint();

    void setGetAllEndpoint(String getAllEndpoint);

}
