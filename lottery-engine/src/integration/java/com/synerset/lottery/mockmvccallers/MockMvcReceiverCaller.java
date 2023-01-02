package com.synerset.lottery.mockmvccallers;

import com.synerset.lottery.infrastructure.controllers.numberreceiver.ReceiverEndpointVersions;
import com.synerset.lottery.infrastructure.objectmappers.JsonConverters;
import com.synerset.lottery.numberreceiver.dto.ReceiverRequestDto;
import com.synerset.lottery.numberreceiver.dto.ReceiverResponseDto;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class MockMvcReceiverCaller {

    private final MockMcvCaller mockMcvCaller;

    public MockMvcReceiverCaller(MockMcvCaller mockMcvCaller) {
        this.mockMcvCaller = mockMcvCaller;
    }

    public List<UUID> sendSomeCouponsToReceiverApi(int amount, List<Integer> numbers) {
        List<UUID> uuidList = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            ReceiverResponseDto receiverResponseDto = makePostCallReceiverInputNumbers(numbers);
            uuidList.add(receiverResponseDto.uuid());
        }
        return uuidList;
    }

    public ReceiverResponseDto makePostCallReceiverInputNumbers(List<Integer> typedNumbers) {
        try {
            ReceiverRequestDto receiverRequestDto = new ReceiverRequestDto(typedNumbers);
            String requestAsJson = JsonConverters.writeObjectAsJsonString(receiverRequestDto);
            MvcResult receiverResponse = mockMcvCaller.makePostMockedCallWithJsonBody(ReceiverEndpointVersions.API_VERSION_V1 +"/numbers", requestAsJson);
            String contentJsonAsString = receiverResponse.getResponse().getContentAsString();
            return JsonConverters.convertJsonResponseToTargetObject(contentJsonAsString, ReceiverResponseDto.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
