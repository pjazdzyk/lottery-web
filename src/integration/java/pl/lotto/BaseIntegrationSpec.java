package pl.lotto;

import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import pl.lotto.numberreceiver.NumberReceiverFacade;
import pl.lotto.numberreceiver.dto.ReceiverDto;
import pl.lotto.resultschecker.ResultsCheckerFacade;
import pl.lotto.timegenerator.*;
import pl.lotto.winningnumbergenerator.WinningNumberGeneratorFacade;

import java.util.List;

@SpringBootTest(classes = AppRunner.class)
@Testcontainers
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
    @Container
    private static final MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:4.0.10"));

    @DynamicPropertySource
    private static void propertyOverride(DynamicPropertyRegistry registry){
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    protected List<ReceiverDto> seedFiveRandomUserInputs(){
        numberReceiverFacade.inputNumbers(List.of(1,2,3,4,5,6));
        numberReceiverFacade.inputNumbers(List.of(1,2,3,4,50,60));
        numberReceiverFacade.inputNumbers(List.of(1,2,3,40,50,60));
        numberReceiverFacade.inputNumbers(List.of(1,2,30,40,50,60));
        numberReceiverFacade.inputNumbers(List.of(1,20,30,40,50,60));
        return numberReceiverFacade.getAllCoupons();
    }

}

