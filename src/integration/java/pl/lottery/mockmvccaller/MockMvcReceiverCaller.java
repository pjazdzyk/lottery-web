package pl.lottery.mockmvccaller;

import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MvcResult;
import pl.lottery.numberreceiver.dto.ReceiverRequestDto;
import pl.lottery.numberreceiver.dto.ReceiverResponseDto;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Component
public class MockMvcReceiverCaller {

    public static final String RECEIVER_URL = "/api/v1/receiver";
    private final JsonConverters jsonConverters;
    private final MockMcvCaller mockMcvCaller;


    public MockMvcReceiverCaller(JsonConverters jsonConverters, MockMcvCaller mockMcvCaller) {
        this.jsonConverters = jsonConverters;
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
            String requestAsJson = jsonConverters.convertReceiverRequestDtoToJsonAsString(receiverRequestDto);
            MvcResult receiverResponse = mockMcvCaller.makePostMockedCallWithJson(RECEIVER_URL, requestAsJson);
            String contentJsonAsString = receiverResponse.getResponse().getContentAsString();
            return jsonConverters.convertJsonResponseToReceiverResponseDto(contentJsonAsString);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
