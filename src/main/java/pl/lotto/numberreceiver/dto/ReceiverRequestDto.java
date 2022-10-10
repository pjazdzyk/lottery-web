package pl.lotto.numberreceiver.dto;

import java.util.List;
public final class ReceiverRequestDto {
    private final List<Integer> typedNumbers;

    public ReceiverRequestDto(List<Integer> typedNumbers) {
        this.typedNumbers = typedNumbers;
    }

    public List<Integer> getUserTypedNumbers() {
        return typedNumbers;
    }

    @Override
    public String toString() {
        return "ReceiverRequestDto{" +
                "typedNumbers=" + typedNumbers +
                '}';
    }
}
