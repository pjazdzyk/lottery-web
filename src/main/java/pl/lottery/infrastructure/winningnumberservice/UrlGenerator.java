package pl.lottery.infrastructure.winningnumberservice;

import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;

public class UrlGenerator {

    private final String drawDateParamName;

    private final WinningServiceConfigurable winningServiceProperties;

    public UrlGenerator(String drawDateParamName, WinningServiceConfigurable winningServiceProperties) {
        this.drawDateParamName = drawDateParamName;
        this.winningServiceProperties = winningServiceProperties;
    }

    public String createUrlForRetrieveCall(LocalDateTime drawDate) {
        return UriComponentsBuilder.newInstance()
                .uriComponents(createUriComponentForEndPointWithParams(
                        winningServiceProperties.getRetrieveEndpoint(),
                        drawDateParamName,
                        drawDate.toString()))
                .build()
                .toString();
    }

    public String createUrlForGenerateCall() {
        return UriComponentsBuilder.newInstance()
                .uriComponents(createUriComponentForEndpoint(winningServiceProperties.getGenerateEndpoint()))
                .build()
                .toString();
    }


    private UriComponents createUriComponentForEndPointWithParams(String endPoint, String name, Object... values) {
        return UriComponentsBuilder.newInstance()
                .uriComponents(createMainUrlComponent())
                .path(endPoint)
                .queryParam(name, values)
                .build();
    }

    private UriComponents createUriComponentForEndpoint(String endPoint) {
        return UriComponentsBuilder.newInstance()
                .uriComponents(createMainUrlComponent())
                .path(endPoint)
                .build();
    }

    private UriComponents createMainUrlComponent() {
        return UriComponentsBuilder.newInstance()
                .scheme(winningServiceProperties.getProtocol())
                .host(winningServiceProperties.getServiceHost())
                .port(winningServiceProperties.getServicePort())
                .path(winningServiceProperties.getServiceApi())
                .build();
    }


}
