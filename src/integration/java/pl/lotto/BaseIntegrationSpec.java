package pl.lotto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import pl.lotto.numberreceiver.NumberReceiverFacade;
import pl.lotto.numberreceiver.dto.ReceiverDto;
import pl.lotto.resultschecker.ResultsCheckerFacade;
import pl.lotto.timegenerator.*;
import pl.lotto.winningnumbergenerator.WinningNumberGeneratorFacade;

import java.util.List;

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

    List<ReceiverDto> seedFiveRandomUserInputs(){
        numberReceiverFacade.inputNumbers(List.of(1,2,3,4,5,6));
        numberReceiverFacade.inputNumbers(List.of(1,2,3,4,50,60));
        numberReceiverFacade.inputNumbers(List.of(1,2,3,40,50,60));
        numberReceiverFacade.inputNumbers(List.of(1,2,30,40,50,60));
        numberReceiverFacade.inputNumbers(List.of(1,20,30,40,50,60));
        return numberReceiverFacade.getAllCoupons();
    }

}

