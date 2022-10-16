package pl.lotto.testutils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MvcResult;
import pl.lotto.resultsannouncer.dto.AnnouncerResponseDto;

import java.util.UUID;

import static pl.lotto.testutils.JsonConverters.convertUuidToRequestDtoAsJson;

@Component
public class MockMvcAnnouncerCaller{

    public static final String API_URL = "/api/v1/results";
    private final ObjectMapper objectMapper;
    private final MockMcvCaller mockMcvCaller;

    public MockMvcAnnouncerCaller(ObjectMapper objectMapper, MockMcvCaller mockMcvCaller) {
        this.objectMapper = objectMapper;
        this.mockMcvCaller = mockMcvCaller;
    }

    public AnnouncerResponseDto retrieveResultsFromAnnouncerApi(UUID uuid) {
        try {
            String requestAsJson = convertUuidToRequestDtoAsJson(uuid);
            MvcResult mvcCallResult = mockMcvCaller.makeControllerCall(API_URL, requestAsJson);
            String contentAsString = mvcCallResult.getResponse().getContentAsString();
            return objectMapper.readValue(contentAsString, AnnouncerResponseDto.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
