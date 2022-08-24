package pl.lotto.numberreceiver;

import java.util.List;
import pl.lotto.numberreceiver.dto.NumberReceiverResultDto;

public class NumberReceiverFacade {

    public NumberReceiverResultDto inputNumbers(List<Integer> numbersFromUser) {
        return new NumberReceiverResultDto("all went good");
    }

}
