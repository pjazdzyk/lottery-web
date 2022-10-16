package pl.lotto.testutils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MvcResult;
import pl.lotto.numberreceiver.dto.ReceiverResponseDto;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static pl.lotto.testutils.JsonConverters.convertListOfNumbersToRequestDtoAsJson;

@Component
public class MockMvcReceiverCaller{

    public static final String API_URL = "/api/v1/receiver";
    private final ObjectMapper objectMapper;
    private final MockMcvCaller mockMcvCaller;


    public MockMvcReceiverCaller(ObjectMapper objectMapper, MockMcvCaller mockMcvCaller) {
        this.objectMapper = objectMapper;
        this.mockMcvCaller = mockMcvCaller;
    }

    public List<UUID> sendSomeCouponsToReceiverApi(int amount, List<Integer> numbers) {
        List<UUID> uuidList = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            ReceiverResponseDto receiverResponseDto = sendOneCouponToReceiverApi(numbers);
            uuidList.add(receiverResponseDto.uuid());
        }
        return uuidList;
    }

    public ReceiverResponseDto sendOneCouponToReceiverApi(List<Integer> typedNumbers) {
        try {
            String jsonRequest = convertListOfNumbersToRequestDtoAsJson(typedNumbers);
            MvcResult mvcResult = mockMcvCaller.makeControllerCall(API_URL, jsonRequest);
            String contentAsString = mvcResult.getResponse().getContentAsString();
            return objectMapper.readValue(contentAsString, ReceiverResponseDto.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
