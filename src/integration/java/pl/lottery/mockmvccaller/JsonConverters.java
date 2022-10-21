package pl.lottery.mockmvccaller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import pl.lottery.numberreceiver.dto.ReceiverRequestDto;
import pl.lottery.numberreceiver.dto.ReceiverResponseDto;
import pl.lottery.resultsannouncer.dto.AnnouncerRequestDto;
import pl.lottery.resultsannouncer.dto.AnnouncerResponseDto;

import java.util.List;
import java.util.UUID;

@Service
class JsonConverters {

    private final ObjectMapper objectMapper;

    public JsonConverters(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public AnnouncerResponseDto convertJsonResponseToAnnouncerResponseDto(String responseAsJson) throws JsonProcessingException {
        return objectMapper.readValue(responseAsJson, AnnouncerResponseDto.class);
    }

    public ReceiverResponseDto convertJsonResponseToReceiverResponseDto(String responseAsJson) throws JsonProcessingException {
        return objectMapper.readValue(responseAsJson, ReceiverResponseDto.class);
    }

    public String convertReceiverRequestDtoToJsonAsString(ReceiverRequestDto receiverRequestDto) throws JsonProcessingException {
        return objectMapper.writeValueAsString(receiverRequestDto);
    }


}
