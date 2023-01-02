package com.synerset.lottery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurekaRunnerRunner {
    public static void main(String[] args) {
        SpringApplication.run(EurekaRunnerRunner.class, args);
    }
}