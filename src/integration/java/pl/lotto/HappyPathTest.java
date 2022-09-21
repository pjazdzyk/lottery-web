package pl.lotto;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import pl.lotto.timegenerator.AdjustableClock;
import pl.lotto.timegenerator.TimeGeneratorFacade;

@SpringBootTest(classes = AppRunner.class)
class HappyPathTest {

    @Autowired
    private ApplicationContext appContext;
    @Autowired
    private AdjustableClock clock;
    @Autowired
    private TimeGeneratorFacade timeGeneratorFacade;

    @Test
    void shouldReturnLotteryResults_whenInputNumbersAreProvided() {
    }

}
