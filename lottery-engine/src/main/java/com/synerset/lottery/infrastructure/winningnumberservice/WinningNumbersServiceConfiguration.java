package com.synerset.lottery.infrastructure.winningnumberservice;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

@Configuration
public class WinningNumbersServiceConfiguration {

    @Bean
    public WinningNumbersGeneratorPortHttpService createForProduction(WinningServiceConfigurable winningServicePropertyConfig, RestTemplate restTemplate) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        WinningNumberUrlGenerator winningNumberUrlGenerator = new WinningNumberUrlGenerator(winningServicePropertyConfig);
        HttpEntityGenerator httpEntityGenerator = new HttpEntityGenerator(headers);
        return new WinningNumbersGeneratorPortHttpService(restTemplate, winningNumberUrlGenerator, httpEntityGenerator);
    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

}

