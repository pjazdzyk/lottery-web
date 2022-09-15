package pl.lotto.numberreceiver;

import pl.lotto.numberreceiver.dto.InputStatus;

import java.util.List;
import java.util.Objects;

class InputValidator {

    public final InputValidatorRules inputValidatorRules;

    InputValidator(InputValidatorRules inputValidatorRules) {
        this.inputValidatorRules = inputValidatorRules;
    }

    InputStatus isValidInput(List<Integer> inputNumbers) {
        if(Objects.isNull(inputNumbers)){
            return InputStatus.getInvalidStatusWithMsg("Null passed as argument.");
        }
        if (doesNotMeetSizeRules(inputNumbers.size())) {
            return InputStatus.getInvalidStatusWithMsg("Invalid number count.");
        }
        if (doesNotMeetNumberBoundary(inputNumbers)) {
            return InputStatus.getInvalidStatusWithMsg("Numbers does not meet game number boundary conditions.");
        }
        if (containsRepeatedNumbers(inputNumbers)) {
            return InputStatus.getInvalidStatusWithMsg("Numbers are not unique.");
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
        return number < inputValidatorRules.minNumberValue() || number > inputValidatorRules.maxNumberValue();
    }

    private boolean containsRepeatedNumbers(List<Integer> inputNumbers) {
        List<Integer> distinctList = inputNumbers.stream().distinct().toList();
        return distinctList.size() < inputValidatorRules.drawnNumbersCount();
    }

    private boolean doesNotMeetSizeRules(int size) {
        return size < inputValidatorRules.drawnNumbersCount() || size > inputValidatorRules.drawnNumbersCount();
    }


}
