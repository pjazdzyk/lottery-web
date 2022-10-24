package pl.lottery.numberreceiver.dto;

import java.util.List;
public class ReceiverRequestDto {
    private List<Integer> typedNumbers;

    public ReceiverRequestDto() {
    }

    public ReceiverRequestDto(List<Integer> typedNumbers) {
        this.typedNumbers = typedNumbers;
    }

    public List<Integer> getTypedNumbers() {
        return typedNumbers;
    }

    public void setTypedNumbers(List<Integer> typedNumbers) {
        this.typedNumbers = typedNumbers;
    }
}
