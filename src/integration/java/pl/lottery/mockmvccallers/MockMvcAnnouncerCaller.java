package pl.lottery.mockmvccallers;

import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MvcResult;
import pl.lottery.infrastructure.objectmappers.JsonConverters;
import pl.lottery.infrastructure.controllers.resultsannouncer.AnnouncerEndpointVersions;
import pl.lottery.resultsannouncer.dto.AnnouncerResponseDto;

import java.util.UUID;

@Component
public class MockMvcAnnouncerCaller {

    private final MockMcvCaller mockMcvCaller;

    public MockMvcAnnouncerCaller(MockMcvCaller mockMcvCaller) {
        this.mockMcvCaller = mockMcvCaller;
    }

    public AnnouncerResponseDto makeGetCallToRetrieveResultsByUuid(UUID uuid) {
        String uuidAsString = uuid.toString();
        try {
            MvcResult announcerResponse = mockMcvCaller.makeGetMockedCallWithPathVariable(AnnouncerEndpointVersions.API_VERSION_V1 + "/results", uuidAsString);
            String contentJsonAsString = announcerResponse.getResponse().getContentAsString();
            return JsonConverters.convertJsonResponseToTargetObject(contentJsonAsString, AnnouncerResponseDto.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }





}
