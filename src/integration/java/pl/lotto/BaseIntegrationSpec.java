package pl.lotto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import pl.lotto.infrastructure.controllers.restapi.NumberReceiverRestController;
import pl.lotto.infrastructure.controllers.restapi.ResultsAnnouncerRestController;
import pl.lotto.numberreceiver.dto.ReceiverRequestDto;
import pl.lotto.numberreceiver.dto.ReceiverResponseDto;
import pl.lotto.resultsannouncer.dto.AnnouncerRequestDto;
import pl.lotto.resultsannouncer.dto.AnnouncerResponseDto;
import pl.lotto.timegenerator.ProgressingAdjustableClock;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

    protected List<UUID> sendSomeCouponsToReceiverApi(int amount, List<Integer> numbers) {
        List<UUID> uuidList = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            ReceiverResponseDto receiverResponseDto = sendOneCouponToReceiverApi(numbers);
            uuidList.add(receiverResponseDto.uuid());
        }
        return uuidList;
    }

    protected ReceiverResponseDto sendOneCouponToReceiverApi(List<Integer> typedNumbers) {
        try {
            String jsonRequest = convertListOfNumbersToRequestDtoAsJson(typedNumbers);
            MvcResult mvcResult = getMvcResponseResult("/api/v1/receiver", jsonRequest);
            String contentAsString = mvcResult.getResponse().getContentAsString();
            return objectMapper.readValue(contentAsString, ReceiverResponseDto.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected AnnouncerResponseDto retrieveResultsFromAnnouncerApiFromUuid(UUID uuid) {
        try {
            String requestAsJson = convertUuidToRequestDtoAsJson(uuid);
            MvcResult mvcCallResult = getMvcResponseResult("/api/v1/results", requestAsJson);
            String contentAsString = mvcCallResult.getResponse().getContentAsString();
            return objectMapper.readValue(contentAsString, AnnouncerResponseDto.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String convertListOfNumbersToRequestDtoAsJson(List<Integer> typedNumbers) throws JsonProcessingException {
        ReceiverRequestDto receiverRequestDto = new ReceiverRequestDto(typedNumbers);
        return objectMapper.writeValueAsString(receiverRequestDto);
    }

    private String convertUuidToRequestDtoAsJson(UUID uuid) throws JsonProcessingException {
        AnnouncerRequestDto requestDto = new AnnouncerRequestDto(uuid);
        return objectMapper.writeValueAsString(requestDto);
    }

    @NotNull
    private MvcResult getMvcResponseResult(String urlTemplate, String callContentAsJson) throws Exception {
        return mockMvc.perform(post(urlTemplate)
                        .content(callContentAsJson)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
    }
}

