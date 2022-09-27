package pl.lotto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class AppRunner {

    public static void main(String[] args) {
        SpringApplication.run(AppRunner.class, args);

    }

}
