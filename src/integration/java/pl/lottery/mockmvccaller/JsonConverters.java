package pl.lottery.mockmvccaller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import pl.lottery.numberreceiver.dto.ReceiverRequestDto;
import pl.lottery.resultsannouncer.dto.AnnouncerRequestDto;

import java.util.List;
import java.util.UUID;

@Service
class JsonConverters {

    private final ObjectMapper objectMapper;

    public JsonConverters(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public String convertListOfNumbersToRequestDtoAsJson(List<Integer> typedNumbers) throws JsonProcessingException {
        ReceiverRequestDto receiverRequestDto = new ReceiverRequestDto(typedNumbers);
        return objectMapper.writeValueAsString(receiverRequestDto);
    }

    public String convertUuidToRequestDtoAsJson(UUID uuid) throws JsonProcessingException {
        AnnouncerRequestDto requestDto = new AnnouncerRequestDto(uuid);
        return objectMapper.writeValueAsString(requestDto);
    }

}
