package pl.lottery.features;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.lottery.BaseIntegrationSpec;
import pl.lottery.DefaultJsonBodies;
import pl.lottery.infrastructure.winningnumberservice.dto.WinningNumberStatus;
import pl.lottery.infrastructure.winningnumberservice.dto.WinningNumbersResponseDto;

import static org.assertj.core.api.Assertions.assertThat;

public class StubbedWinningNumberServiceTestIT extends BaseIntegrationSpec {

    @Test
    @DisplayName("stubbed winning numbers service returns expected response bodies when http call is made")
    public void stubbedWinningNumbersServiceReturnsExpectedResponseBodies_WhenHttpCallIsMade(){
        // given
        stubPostCallToGenerateEndpoint(DefaultJsonBodies.winningNumsDefaultRequest, DefaultJsonBodies.winningNumsDefaultGenerateResponse);
        stubGetCallToRetrieveEndpointWithParam(DefaultJsonBodies.winningNumsDefaultRetrieveResponse, DRAW_DATE.toString());

        // when
        WinningNumbersResponseDto generateResponseDto = winningNumbersGeneratorService.generateWinningNumbers(DRAW_DATE);
        WinningNumbersResponseDto retrieveResponseDto = winningNumbersGeneratorService.retrieveWinningNumbers(DRAW_DATE);

        // then
        assertThat(generateResponseDto.drawDate()).isEqualTo(retrieveResponseDto.drawDate()).isEqualTo(DRAW_DATE);
        assertThat(generateResponseDto.winningNumbers()).isEqualTo(retrieveResponseDto.winningNumbers()).isEqualTo(WINNERS_TYPED_NUMBERS);
        assertThat(generateResponseDto.status()).isEqualTo(WinningNumberStatus.GENERATED);
        assertThat(retrieveResponseDto.status()).isEqualTo(WinningNumberStatus.LOADED_FROM_DB);
    }

}
