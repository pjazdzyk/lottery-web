package com.synerset.lottery;

import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import com.synerset.lottery.mockmvccallers.MockMvcAnnouncerCaller;
import com.synerset.lottery.mockmvccallers.MockMvcReceiverCaller;
import com.synerset.lottery.resultschecker.ResultsCheckerFacade;
import com.synerset.lottery.resultschecker.WinningNumberGeneratorPort;
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

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

@Testcontainers
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BaseIntegrationSpec implements IntegrationTestConstants {


    protected static final int ORIGINAL_REDIS_PORT = 6379;
    protected static final String WIREMOCK_SERVER_HOST = "localhost";
    protected static final String WINNING_NUMBERS_SERVICE_PATH = "/api/v1/winning-numbers/";

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
        registry.add("spring.data.redis.host", redis::getHost);
        registry.add("spring.data.redis.port", () -> redis.getMappedPort(ORIGINAL_REDIS_PORT).toString());
        registry.add("winning-numbers-service.baseUrl", () -> WIREMOCK_SERVER_HOST + ":" + wireMockServer.getPort());
    }

    @AfterEach
    void afterEach() {
       wireMockServer.resetAll();
    }

    protected static void stubGetCallToRetrieveEndpointWithParam(String responseForGenerateBodyAsJson, String drawDate) {
        String apiURL = WINNING_NUMBERS_SERVICE_PATH + drawDate;
        wireMockServer.stubFor(WireMock.get(apiURL)
                .willReturn(createResponseBuilderWithResponseBodyAsJson(responseForGenerateBodyAsJson)
                ));
    }

    protected static void stubPostCallToGenerateEndpoint(String requestBodyAsJson, String responseForGenerateBodyAsJson) {
        wireMockServer.stubFor(WireMock.post(WINNING_NUMBERS_SERVICE_PATH)
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


