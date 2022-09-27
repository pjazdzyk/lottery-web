package pl.lotto.timegenerator;

import org.springframework.boot.convert.ApplicationConversionService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;

import java.time.*;
import java.time.format.DateTimeFormatter;

@Configuration
class ConversionServiceConfiguration {

    @Bean
    public ConversionService conversionService() {
        ApplicationConversionService conversionService = new ApplicationConversionService();
        conversionService.addConverter(String.class, LocalTime.class, source -> LocalTime.parse(source, DateTimeFormatter.ISO_LOCAL_TIME));
        conversionService.addConverter(String.class, LocalDate.class, source -> LocalDate.parse(source, DateTimeFormatter.ISO_DATE));
        conversionService.addConverter(Integer.class, Duration.class, source -> Duration.ofDays(source));
        conversionService.addConverter(String.class, ZoneId.class, ZoneId::of);
        return conversionService;
    }

}
