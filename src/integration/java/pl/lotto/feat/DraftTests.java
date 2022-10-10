package pl.lotto.feat;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pl.lotto.BaseIntegrationSpec;
import pl.lotto.numberreceiver.dto.ReceiverRequestDto;
import pl.lotto.numberreceiver.dto.ReceiverResponseDto;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class DraftTests extends BaseIntegrationSpec {

    private final ReceiverRequestDto receiverRequestDto = new ReceiverRequestDto(List.of(1, 2, 3, 4, 5, 6));

    @Test
    public void testHomePage() throws Exception {

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/receiver")
                        .content(receiverRequestDto.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();
        ReceiverResponseDto numberReceiverResultDto =
                objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ReceiverResponseDto.class);

        assertThat(numberReceiverResultDto.uuid()).isNotNull();
    }


}
