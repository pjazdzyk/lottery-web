package pl.lottery;

import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import pl.lottery.mockmvccallers.MockMvcAnnouncerCaller;
import pl.lottery.mockmvccallers.MockMvcReceiverCaller;
import pl.lottery.resultschecker.ResultsCheckerFacade;
import pl.lottery.resultschecker.WinningNumberGeneratorPort;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

@Testcontainers
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BaseIntegrationSpec implements IntegrationTestConstants {


    protected static final int ORIGINAL_REDIS_PORT = 6379;
    protected static final String WIREMOCK_SERVER_HOST = "localhost";
    protected static final String WINNING_NUMBERS_GENERATE_API = "/api/v1/generate";
    protected static final String WINNING_NUMBERS_RETRIEVE_API = "/api/v1/numbers?drawDate=%s";
    @Container
    protected static MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:4.0.10"));

    protected static GenericContainer<?> redis;

    static {
        redis = new GenericContainer<>(DockerImageName.parse("redis:5.0.3-alpine")).withExposedPorts(ORIGINAL_REDIS_PORT);
        redis.start();
    }

    @Autowired
    protected MockMvcAnnouncerCaller mockMvcAnnouncerCaller;
    @Autowired
    protected MockMvcReceiverCaller mockMvcReceiverCaller;
    @Autowired
    protected WinningNumberGeneratorPort winningNumbersGeneratorService;
    @Autowired
    protected ResultsCheckerFacade resultsCheckerFacade;

    @RegisterExtension
    protected static WireMockExtension wireMockServer = WireMockExtension.newInstance()
            .options(wireMockConfig().dynamicPort())
            .build();

    @DynamicPropertySource
    private static void propertyOverride(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
        registry.add("spring.redis.host", redis::getHost);
        registry.add("spring.redis.port", () -> redis.getMappedPort(ORIGINAL_REDIS_PORT).toString());
        registry.add("winning-numbers-service.serviceHost", () -> WIREMOCK_SERVER_HOST);
        registry.add("winning-numbers-service.servicePort", () -> wireMockServer.getPort());
    }

    @AfterEach
    void afterEach() {
       wireMockServer.resetAll();
    }

    protected static void stubGetCallToRetrieveEndpointWithParam(String responseForGenerateBodyAsJson, String drawDate) {
        wireMockServer.stubFor(WireMock.get(String.format(WINNING_NUMBERS_RETRIEVE_API, drawDate))
                .willReturn(createResponseBuilderWithResponseBodyAsJson(responseForGenerateBodyAsJson)
                ));
    }

    protected static void stubPostCallToGenerateEndpoint(String requestBodyAsJson, String responseForGenerateBodyAsJson) {
        wireMockServer.stubFor(WireMock.post(WINNING_NUMBERS_GENERATE_API)
                .withRequestBody(WireMock.equalTo(requestBodyAsJson))
                .willReturn(createResponseBuilderWithResponseBodyAsJson(responseForGenerateBodyAsJson)
                ));
    }

    private static ResponseDefinitionBuilder createResponseBuilderWithResponseBodyAsJson(String responseForGenerateBodyAsJson) {
        return WireMock.aResponse()
                .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .withBody(responseForGenerateBodyAsJson);
    }

}


