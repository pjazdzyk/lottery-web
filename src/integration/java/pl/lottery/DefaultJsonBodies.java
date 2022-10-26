package pl.lottery;

import pl.lottery.infrastructure.objectmappers.JsonConverters;
import pl.lottery.infrastructure.winningnumberservice.dto.WinningNumberStatus;
import pl.lottery.infrastructure.winningnumberservice.dto.WinningNumbersRequestDto;
import pl.lottery.infrastructure.winningnumberservice.dto.WinningNumbersResponseDto;

public class DefaultJsonBodies implements IntegrationTestConstants {

    public static String winningNumsDefaultRequest = JsonConverters.writeObjectAsJsonString(new WinningNumbersRequestDto(DRAW_DATE));
    public static String winningNumsDefaultGenerateResponse = JsonConverters.writeObjectAsJsonString(new WinningNumbersResponseDto(DRAW_DATE,
            WINNERS_TYPED_NUMBERS,
            WinningNumberStatus.GENERATED)
    );
    public static String winningNumsDefaultRetrieveResponse = JsonConverters.writeObjectAsJsonString(new WinningNumbersResponseDto(DRAW_DATE,
            WINNERS_TYPED_NUMBERS,
            WinningNumberStatus.LOADED_FROM_DB)
    );

}
