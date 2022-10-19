package pl.lottery.winningnumbergenerator;

import org.springframework.context.annotation.Bean;
import pl.lottery.infrastructure.winningnumberservice.WinningNumberGenerable;
import pl.lottery.resultschecker.WinningNumbersServiceStub;

import java.util.List;

public class WinningNumberServiceConfiguration {

    public static final List<Integer> WINING_NUMBERS = List.of(1, 2, 3, 4, 5, 6);

    @Bean
    public WinningNumberGenerable createWinningNumbersServiceStub() {
        return new WinningNumbersServiceStub(WINING_NUMBERS);
    }


}
