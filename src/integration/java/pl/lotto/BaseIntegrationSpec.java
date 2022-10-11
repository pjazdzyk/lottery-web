package pl.lotto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import pl.lotto.infrastructure.controllers.restapi.NumberReceiverRestController;
import pl.lotto.infrastructure.controllers.restapi.ResultsAnnouncerRestController;
import pl.lotto.timegenerator.ProgressingAdjustableClock;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = AppRunner.class)
@Testcontainers
@AutoConfigureMockMvc
public class BaseIntegrationSpec {
    @Autowired
    protected ProgressingAdjustableClock adjustableClock;

    @Autowired
    protected NumberReceiverRestController numberReceiverRestController;

    @Autowired
    protected ResultsAnnouncerRestController resultsAnnouncerRestController;

    @Autowired
    protected WebApplicationContext webApplicationContext;
    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected MockMvc mockMvc;

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

    protected int sendSomeCouponsToReceiverApi(int amount, String jsonRequest){
        int counter = 0;
        for(int i=0; i<amount; i++){
            sendOneCouponToReceiverApi(jsonRequest);
            counter++;
        }
        return counter;
    }

    private void sendOneCouponToReceiverApi(String jsonRequest) {
        try {
            mockMvc.perform(post("/api/v1/receiver")
                            .content(jsonRequest)
                            .contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(status().isOk())
                    .andReturn();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}

