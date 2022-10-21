package pl.lottery.feat.winningnumbergenerator;

import org.springframework.context.annotation.Bean;
import pl.lottery.resultschecker.WinningNumberGeneratorPort;
import pl.lottery.resultschecker.WinningNumbersServiceStub;

import java.util.List;

public class WinningNumberServiceConfiguration {

    public static final List<Integer> WINING_NUMBERS = List.of(1, 2, 3, 4, 5, 6);

    @Bean
    public WinningNumberGeneratorPort createWinningNumbersServiceStub() {
        return new WinningNumbersServiceStub(WINING_NUMBERS);
    }


}
