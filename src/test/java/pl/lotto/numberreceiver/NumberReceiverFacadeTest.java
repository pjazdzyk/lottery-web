package pl.lotto.numberreceiver;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.lotto.numberreceiver.dto.NumberReceiverResultDto;

import static org.assertj.core.api.Assertions.assertThat;

class NumberReceiverFacadeTest {

    @Test
    @DisplayName("input user numbers and returns dto with correct user numbers")
    public void inputNumbers_givenInputNumbers_returnsResultDTOWithUserNumbers() {
        // given
        NumberReceiverFacade numberReceiverFacade = new NumberReceiverFacade();
        List<Integer> numbersFromUser = List.of(1, 2, 3, 4, 5, 6);

        // when
        NumberReceiverResultDto result = numberReceiverFacade.inputNumbers(numbersFromUser);
        List<Integer> actualUserNumbers = result.typedNumbers();

        // then
        assertThat(actualUserNumbers).isEqualTo(numbersFromUser);
    }

}
