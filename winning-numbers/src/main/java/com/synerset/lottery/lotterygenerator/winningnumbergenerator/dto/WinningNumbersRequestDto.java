package com.synerset.lottery.lotterygenerator.winningnumbergenerator.dto;

import java.time.LocalDateTime;

public final class WinningNumbersRequestDto {
    private LocalDateTime drawDate;

    public WinningNumbersRequestDto() {
    }

    public WinningNumbersRequestDto(LocalDateTime drawDate) {
        this.drawDate = drawDate;
    }

    public LocalDateTime getDrawDate() {
        return drawDate;
    }

    public void setDrawDate(LocalDateTime drawDate) {
        this.drawDate = drawDate;
    }
}