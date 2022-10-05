package pl.lotto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import pl.lotto.numberreceiver.NumberReceiverFacade;
import pl.lotto.numberreceiver.dto.ReceiverDto;
import pl.lotto.resultsannouncer.ResultsAnnouncerFacade;
import pl.lotto.resultschecker.ResultsCheckerFacade;
import pl.lotto.timegenerator.ProgressingAdjustableClock;
import pl.lotto.timegenerator.TimeGeneratorFacade;
import pl.lotto.winningnumbergenerator.WinningNumberGeneratorFacade;

import java.util.List;

@SpringBootTest(classes = AppRunner.class)
@Testcontainers
public class BaseIntegrationSpec {

    @Autowired
    public ApplicationContext appContext;
    @Autowired
    public ProgressingAdjustableClock adjustableClock;
    @Autowired
    public TimeGeneratorFacade timeGeneratorFacade;
    @Autowired
    public NumberReceiverFacade numberReceiverFacade;
    @Autowired
    public WinningNumberGeneratorFacade winningNumberGeneratorFacade;
    @Autowired
    public ResultsCheckerFacade resultsCheckerFacade;

    @Autowired
    public ResultsAnnouncerFacade resultsAnnouncerFacade;

    @Container
    private static final MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:4.0.10"));

    @Container
    private static final GenericContainer<?> redis = new GenericContainer<>(DockerImageName.parse("redis:5.0.3-alpine")).withExposedPorts(6379);

    @DynamicPropertySource
    private static void propertyOverride(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
        registry.add("spring.redis.host", redis::getHost);
        registry.add("spring.redis.port", () -> redis.getMappedPort(6379).toString());
    }

    protected List<ReceiverDto> seedFiveRandomUserInputs() {
        numberReceiverFacade.inputNumbers(List.of(1, 2, 3, 4, 5, 6));
        numberReceiverFacade.inputNumbers(List.of(1, 2, 3, 4, 50, 60));
        numberReceiverFacade.inputNumbers(List.of(1, 2, 3, 40, 50, 60));
        numberReceiverFacade.inputNumbers(List.of(1, 2, 30, 40, 50, 60));
        numberReceiverFacade.inputNumbers(List.of(1, 20, 30, 40, 50, 60));
        return numberReceiverFacade.getAllCoupons();
    }

}

