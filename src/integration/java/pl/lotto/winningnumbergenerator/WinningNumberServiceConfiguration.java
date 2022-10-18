package pl.lotto.winningnumbergenerator;

import org.springframework.context.annotation.Bean;
import pl.lotto.infrastructure.winningnumberservice.WinningNumberGenerable;
import pl.lotto.resultschecker.WinningNumbersServiceStub;

import java.util.List;

public class WinningNumberServiceConfiguration {

    public static final List<Integer> WINING_NUMBERS = List.of(1, 2, 3, 4, 5, 6);

    @Bean
    public WinningNumberGenerable createWinningNumbersServiceStub() {
        return new WinningNumbersServiceStub(WINING_NUMBERS);
    }


}
