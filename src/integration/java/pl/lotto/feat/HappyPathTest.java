package pl.lotto.feat;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.lotto.BaseIntegrationSpec;
import pl.lotto.numberreceiver.NumberReceiverFacade;
import pl.lotto.numberreceiver.dto.ReceiverRequestDto;
import pl.lotto.numberreceiver.dto.ReceiverResponseDto;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

class HappyPathTest extends BaseIntegrationSpec {

    private final static ReceiverRequestDto winningRequestDto = new ReceiverRequestDto(List.of(1, 2, 3, 4, 5, 6));
    private final static ReceiverRequestDto loserRequestDto = new ReceiverRequestDto(List.of(20, 30, 40, 50, 60, 70));
    private String jsonWinningCouponRequest;
    private String jsonWLosingRequest;

    @Autowired
    NumberReceiverFacade numberReceiverFacade;

    @Before
    public void setUp(){
        try {
            jsonWinningCouponRequest = objectMapper.writeValueAsString(winningRequestDto);
            jsonWLosingRequest = objectMapper.writeValueAsString(loserRequestDto);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testHomePage() throws Exception {
        String jsonRequest = objectMapper.writeValueAsString(winningRequestDto);
        sendSomeCouponsToReceiverApi(10, jsonRequest);

        List<ReceiverResponseDto> allCoupons = numberReceiverFacade.getAllCoupons();
        System.out.println(allCoupons);

    }

}
