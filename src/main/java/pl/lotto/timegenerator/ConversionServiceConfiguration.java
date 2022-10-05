package pl.lotto.timegenerator;

import org.springframework.boot.convert.ApplicationConversionService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;

import java.time.*;

@Configuration
class ConversionServiceConfiguration {

    @Bean
    public ConversionService conversionService() {
        ApplicationConversionService conversionService = new ApplicationConversionService();
        conversionService.addConverter(Integer.class, DayOfWeek.class, DayOfWeek::of);
        conversionService.addConverter(String.class, LocalTime.class, this::parseStringToLocalTime);
        conversionService.addConverter(String.class, LocalDate.class, LocalDate::parse);
        conversionService.addConverter(Integer.class, Duration.class, source -> Duration.ofDays(source));
        conversionService.addConverter(String.class, ZoneId.class, ZoneId::of);
        return conversionService;
    }

    private LocalTime parseStringToLocalTime(String localTimeAsString) {
        String[] components = localTimeAsString.split(":");
        int hours = Integer.parseInt(components[0]);
        int minutes = Integer.parseInt(components[1]);
        int seconds = Integer.parseInt(components[2]);
        return LocalTime.of(hours, minutes, seconds);
    }

}
