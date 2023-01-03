package com.synerset.lottery;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfiguration {
    @Bean
    @Profile("integration")
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

}
