package pl.lotto.testutils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import pl.lotto.numberreceiver.dto.ReceiverResponseDto;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static pl.lotto.testutils.JsonConverters.convertListOfNumbersToRequestDtoAsJson;

@Component
public class MockMvcReceiverCaller extends MockMcvCaller{

    private final ObjectMapper objectMapper;

    public MockMvcReceiverCaller(MockMvc mockMvc, ObjectMapper objectMapper) {
        super(mockMvc);
        this.objectMapper = objectMapper;
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
            MvcResult mvcResult = makeControllerCall("/api/v1/receiver", jsonRequest);
            String contentAsString = mvcResult.getResponse().getContentAsString();
            return objectMapper.readValue(contentAsString, ReceiverResponseDto.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
