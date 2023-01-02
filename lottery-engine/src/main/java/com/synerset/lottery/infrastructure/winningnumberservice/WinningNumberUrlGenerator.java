package com.synerset.lottery.infrastructure.winningnumberservice;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;

@Configuration
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
                .host(winningServiceProperties.getBaseUrl())
                .path(winningServiceProperties.getServiceApi())
                .path(winningServiceProperties.getEndPointPath())
                .build();
    }

}
