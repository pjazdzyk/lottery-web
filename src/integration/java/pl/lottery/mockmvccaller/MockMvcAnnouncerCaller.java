package pl.lottery.mockmvccaller;

import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MvcResult;
import pl.lottery.resultsannouncer.dto.AnnouncerResponseDto;

import java.util.UUID;

@Component
public class MockMvcAnnouncerCaller {

    public static final String ANNOUNCER_URL = "/api/v1/results";
    private final JsonConverters jsonConverters;
    private final MockMcvCaller mockMcvCaller;

    public MockMvcAnnouncerCaller(JsonConverters jsonConverters, MockMcvCaller mockMcvCaller) {
        this.jsonConverters = jsonConverters;
        this.mockMcvCaller = mockMcvCaller;
    }

    public AnnouncerResponseDto makeGetCallToRetrieveResultsByUuid(UUID uuid) {
        String uuidAsString = uuid.toString();
        try {
            MvcResult announcerResponse = mockMcvCaller.makeGetMockedCallWithParam(ANNOUNCER_URL, "requestUuid", uuidAsString);
            String contentJsonAsString = announcerResponse.getResponse().getContentAsString();
            return jsonConverters.convertJsonResponseToAnnouncerResponseDto(contentJsonAsString);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }





}
