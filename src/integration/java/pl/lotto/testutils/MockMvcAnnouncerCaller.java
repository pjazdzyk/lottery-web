package pl.lotto.testutils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import pl.lotto.resultsannouncer.dto.AnnouncerResponseDto;

import java.util.UUID;

import static pl.lotto.testutils.JsonConverters.convertUuidToRequestDtoAsJson;

@Component
public class MockMvcAnnouncerCaller extends MockMcvCaller{

    private final ObjectMapper objectMapper;

    public MockMvcAnnouncerCaller(MockMvc mockMvc, ObjectMapper objectMapper) {
        super(mockMvc);
        this.objectMapper = objectMapper;
    }

    public AnnouncerResponseDto retrieveResultsFromAnnouncerApi(UUID uuid) {
        try {
            String requestAsJson = convertUuidToRequestDtoAsJson(uuid);
            MvcResult mvcCallResult = makeControllerCall("/api/v1/results", requestAsJson);
            String contentAsString = mvcCallResult.getResponse().getContentAsString();
            return objectMapper.readValue(contentAsString, AnnouncerResponseDto.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
