package com.synerset.lottery.lotterygenerator.mockedcallers;

import com.synerset.lottery.lotterygenerator.winningnumbergenerator.dto.WinningNumbersResponseDto;
import org.springframework.stereotype.Service;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDateTime;

@Service
public class MockMvcWinningGeneratorCaller {

    public static final String API_URL = "/api/v1/winning-numbers";
    private final MockMcvCaller mockMcvCaller;
    private final JsonConverters jsonConverters;

    public MockMvcWinningGeneratorCaller(MockMcvCaller mockMcvCaller, JsonConverters jsonConverters) {
        this.mockMcvCaller = mockMcvCaller;
        this.jsonConverters = jsonConverters;
    }

    public WinningNumbersResponseDto mockedPostCallToGenerateWinningNumbers(LocalDateTime drawDate) {
        try {
            String requestBodyAsJson = jsonConverters.convertDateTimeToWinningNumbersRequestBody(drawDate);
            MvcResult mvcCallResult = mockMcvCaller.makePostMockedCallWithJson(API_URL, requestBodyAsJson);
            String responseAsJson = mvcCallResult.getResponse().getContentAsString();
            return jsonConverters.convertJsonResponseToWinningNumbersDto(responseAsJson);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public WinningNumbersResponseDto mockedGetCallToRetrieveNumbers(LocalDateTime drawDate) {
        try {
            String drawDateAsString = drawDate.toString();
            MvcResult mvcCallResult = mockMcvCaller.makeGetMockedCallWithPathVariable(API_URL, drawDateAsString);
            String responseAsJson = mvcCallResult.getResponse().getContentAsString();
            return jsonConverters.convertJsonResponseToWinningNumbersDto(responseAsJson);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}