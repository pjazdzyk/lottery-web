package pl.lottery.timegenerator;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.time.*;

@Configuration
public class TimeGeneratorConfiguration {

    public TimeGeneratorFacade createForTest(Clock clockForTest, TimeConfigurable timeSpec) {
        return createTimeGeneratorFacade(clockForTest, timeSpec);
    }

    @Bean
    @Profile("dev")
    public TimeGeneratorFacade createTimeFacadeForDevelopment(TimeConfigurable timeSpec) {
        timeSpec.setDrawDayOfWeek(LocalDateTime.now().getDayOfWeek());
        timeSpec.setDrawTime(LocalTime.now().minusMinutes(2));
        Clock developmentClock = Clock.systemUTC();
        return createTimeGeneratorFacade(developmentClock, timeSpec);
    }

    @Bean
    @Profile("production")
    public TimeGeneratorFacade createTimeFacadeForProduction(TimeConfigurable timeSpec) {
        Clock productionClock = Clock.systemUTC();
        return createTimeGeneratorFacade(productionClock, timeSpec);
    }

    public TimeGeneratorFacade createTimeGeneratorFacade(Clock clock, TimeConfigurable timeSpec) {
        ExpirationDateTimeGenerator expirationDateTimeGenerator = new ExpirationDateTimeGenerator(timeSpec.getExpirationInDays());
        CurrentDateTimeGenerator currentDateTimeGenerator = new CurrentDateTimeGenerator(clock);
        DrawDateTimeGenerator drawDateTimeGenerator = new DrawDateTimeGenerator(timeSpec.getDrawDayOfWeek(), timeSpec.getDrawTime());
        TimeGenerator timeGenerator = new TimeGenerator(currentDateTimeGenerator, drawDateTimeGenerator, expirationDateTimeGenerator);
        return new TimeGeneratorFacade(timeGenerator);
    }


}
