package pl.lottery.resultschecker.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public final class CheckerDto {
    private final UUID uuid;
    private final LocalDateTime drawDate;
    private final List<Integer> typedNumbers;
    private final List<Integer> winningNumbers;
    private final List<Integer> matchedNumbers;
    private final boolean isWinner;
    private final CheckerStatus status;

    private CheckerDto(CheckerDtoBuilder builder){
        this.uuid = builder.uuid;
        this.drawDate =builder.drawDate;
        this.typedNumbers = builder.typedNumbers;
        this.winningNumbers = builder.winningNumbers;
        this.matchedNumbers = builder.matchedNumbers;
        this.isWinner = builder.isWinner;
        this.status = builder.status;
    }

    public UUID uuid() {
        return uuid;
    }

    public LocalDateTime drawDate() {
        return drawDate;
    }

    public List<Integer> typedNumbers() {
        return typedNumbers;
    }

    public List<Integer> winningNumbers() {
        return winningNumbers;
    }

    public List<Integer> matchedNumbers() {
        return matchedNumbers;
    }

    public boolean isWinner() {
        return isWinner;
    }

    public CheckerStatus status() {
        return status;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (CheckerDto) obj;
        return Objects.equals(this.uuid, that.uuid) &&
                Objects.equals(this.drawDate, that.drawDate) &&
                Objects.equals(this.typedNumbers, that.typedNumbers) &&
                Objects.equals(this.winningNumbers, that.winningNumbers) &&
                Objects.equals(this.matchedNumbers, that.matchedNumbers) &&
                this.isWinner == that.isWinner &&
                Objects.equals(this.status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, drawDate, typedNumbers, winningNumbers, matchedNumbers, isWinner, status);
    }

    @Override
    public String toString() {
        return "CheckerDto[" +
                "uuid=" + uuid + ", " +
                "drawDate=" + drawDate + ", " +
                "typedNumbers=" + typedNumbers + ", " +
                "winningNumbers=" + winningNumbers + ", " +
                "matchedNumbers=" + matchedNumbers + ", " +
                "isWinner=" + isWinner + ", " +
                "status=" + status + ']';
    }

    public static CheckerDto ofNotFoundDtoForUuid(UUID uuid){
        return CheckerDto.CheckerDtoBuilder.newInstance(CheckerStatus.NOT_FOUND).withUid(uuid).build();
    }

    public static class CheckerDtoBuilder{

        private UUID uuid;
        private LocalDateTime drawDate;
        private List<Integer> typedNumbers;
        private List<Integer> winningNumbers;
        private List<Integer> matchedNumbers;
        private boolean isWinner;
        private CheckerStatus status;

        public static CheckerDtoBuilder newInstance(CheckerStatus status){
            CheckerDtoBuilder checkerDtoBuilder = new CheckerDtoBuilder();
            checkerDtoBuilder.status = status;
            return checkerDtoBuilder;
        }

        public CheckerDtoBuilder withUid(UUID uuid){
            this.uuid = uuid;
            return this;
        }

        public CheckerDtoBuilder withDrawDate(LocalDateTime drawDate){
            this.drawDate = drawDate;
            return this;
        }

        public CheckerDtoBuilder withTypedNumbers(List<Integer> typedNumbers){
            this.typedNumbers = typedNumbers;
            return this;
        }

        public CheckerDtoBuilder withWinningNumbers(List<Integer> winningNumbers){
            this.winningNumbers = winningNumbers;
            return this;
        }

        public CheckerDtoBuilder withMatchedNumbers(List<Integer> matchedNumbers){
            this.matchedNumbers = matchedNumbers;
            return this;
        }

        public CheckerDtoBuilder withIsWinner(boolean isWinner){
            this.isWinner = isWinner;
            return this;
        }

        public CheckerDto build(){
            return new CheckerDto(this);
        }


    }

}
