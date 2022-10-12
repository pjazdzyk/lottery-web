package pl.lotto.testutils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import pl.lotto.numberreceiver.dto.ReceiverRequestDto;
import pl.lotto.resultsannouncer.dto.AnnouncerRequestDto;

import java.util.List;
import java.util.UUID;

public class JsonConverters {

    private static final ObjectMapper objectMapper = new ObjectMapper();

   public static String convertListOfNumbersToRequestDtoAsJson(List<Integer> typedNumbers) throws JsonProcessingException {
        ReceiverRequestDto receiverRequestDto = new ReceiverRequestDto(typedNumbers);
        return objectMapper.writeValueAsString(receiverRequestDto);
    }

    public static String convertUuidToRequestDtoAsJson(UUID uuid) throws JsonProcessingException {
        AnnouncerRequestDto requestDto = new AnnouncerRequestDto(uuid);
        return objectMapper.writeValueAsString(requestDto);
    }

}
