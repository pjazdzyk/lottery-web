package pl.lotto.numberreceiver;

import pl.lotto.numberreceiver.dto.InputStatus;

import java.util.List;
import java.util.Objects;

class InputValidator {

    public final LottoInputRules lottoInputRules;

    InputValidator(LottoInputRules lottoInputRules) {
        this.lottoInputRules = lottoInputRules;
    }

    InputStatus validateInput(List<Integer> inputNumbers) {
        if (Objects.isNull(inputNumbers) || doesNotMeetSizeRules(inputNumbers.size())
                || doesNotMeetNumberBoundary(inputNumbers) || containsRepeatedNumbers(inputNumbers)) {
            return InputStatus.INVALID;
        }
        return InputStatus.VALIDATED;
    }

    private boolean doesNotMeetNumberBoundary(List<Integer> inputNumbers) {
        for (Integer number : inputNumbers) {
            if (isNegative(number) || doesNotMeetMinMaxRules(number)) {
                return true;
            }
        }
        return false;
    }

    private boolean isNegative(int number) {
        return number < 0;
    }

    private boolean doesNotMeetMinMaxRules(int number) {
        return number < lottoInputRules.minNumberValue() || number > lottoInputRules.maxNumberValue();
    }

    private boolean containsRepeatedNumbers(List<Integer> inputNumbers) {
        List<Integer> distinctList = inputNumbers.stream().distinct().toList();
        return distinctList.size() < lottoInputRules.drawnNumbersCount();
    }

    private boolean doesNotMeetSizeRules(int size) {
        return size < lottoInputRules.drawnNumbersCount() || size > lottoInputRules.drawnNumbersCount();
    }


}
