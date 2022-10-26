package pl.lottery.mockmvccallers;

import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MvcResult;
import pl.lottery.infrastructure.controllers.numberreceiver.ReceiverEndpointVersions;
import pl.lottery.infrastructure.objectmappers.JsonConverters;
import pl.lottery.numberreceiver.dto.ReceiverRequestDto;
import pl.lottery.numberreceiver.dto.ReceiverResponseDto;

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
            MvcResult receiverResponse = mockMcvCaller.makePostMockedCallWithJson(ReceiverEndpointVersions.API_VERSION_V1 +"/receiver", requestAsJson);
            String contentJsonAsString = receiverResponse.getResponse().getContentAsString();
            return JsonConverters.convertJsonResponseToTargetObject(contentJsonAsString, ReceiverResponseDto.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
