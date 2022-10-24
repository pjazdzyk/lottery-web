package pl.lottery.infrastructure.objectmapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ObjectMapperConfiguration {

    @Bean
    ObjectMapper createObjectMapper(){
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        return objectMapper;
    }

}
