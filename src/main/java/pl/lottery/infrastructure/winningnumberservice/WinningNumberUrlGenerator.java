package pl.lottery.infrastructure.winningnumberservice;

import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;

class WinningNumberUrlGenerator {

    private final WinningServiceConfigurable winningServiceProperties;

    WinningNumberUrlGenerator(WinningServiceConfigurable winningServiceProperties) {
        this.winningServiceProperties = winningServiceProperties;
    }

    String createUrlForRetrieveCall(LocalDateTime drawDate) {
        return UriComponentsBuilder.newInstance()
                .uriComponents(createMainUrlComponent())
                .path("/" + drawDate.toString())
                .build()
                .toString();
    }

    String createUrlForGenerateCall() {
        return createMainUrlComponent().toString();
    }

    private UriComponents createMainUrlComponent() {
        return UriComponentsBuilder.newInstance()
                .scheme(winningServiceProperties.getProtocol())
                .host(winningServiceProperties.getServiceHost())
                .port(winningServiceProperties.getServicePort())
                .path(winningServiceProperties.getServiceApi())
                .path(winningServiceProperties.getEndPointPath())
                .build();
    }

}
