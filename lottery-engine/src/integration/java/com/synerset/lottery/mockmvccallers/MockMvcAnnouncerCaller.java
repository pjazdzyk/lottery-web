package com.synerset.lottery.mockmvccallers;

import com.synerset.lottery.infrastructure.controllers.resultsannouncer.AnnouncerEndpointVersions;
import com.synerset.lottery.infrastructure.objectmappers.JsonConverters;
import com.synerset.lottery.resultsannouncer.dto.AnnouncerResponseDto;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MvcResult;

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
