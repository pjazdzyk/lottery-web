package pl.lotto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import pl.lotto.numberreceiver.NumberReceiverFacade;
import pl.lotto.resultschecker.ResultsCheckerFacade;
import pl.lotto.timegenerator.*;
import pl.lotto.winningnumbergenerator.WinningNumberGeneratorFacade;

@SpringBootTest(classes = AppRunner.class)
public class BaseIntegrationSpec {

    @Autowired
    public ApplicationContext appContext;
    @Autowired
    public AdjustableClock adjustableClock;
    @Autowired
    public TimeGeneratorFacade timeGeneratorFacade;
    @Autowired
    public NumberReceiverFacade numberReceiverFacade;
    @Autowired
    public WinningNumberGeneratorFacade winningNumberGeneratorFacade;
    @Autowired
    public ResultsCheckerFacade resultsCheckerFacade;

}

