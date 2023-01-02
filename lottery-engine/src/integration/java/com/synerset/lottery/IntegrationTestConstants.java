package com.synerset.lottery;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.List;

public interface IntegrationTestConstants {
    List<Integer> WINNERS_TYPED_NUMBERS = List.of(1, 2, 3, 4, 5, 6);
    List<Integer> LOSERS_TYPED_NUMBERS = List.of(20, 30, 40, 50, 60, 70);
    LocalDateTime DRAW_DATE = LocalDateTime.of(2022, 8, 13, 12, 0);
    LocalDate INITIAL_DATE = LocalDate.of(2022, 8, 8);
    LocalTime INITIAL_TIME = LocalTime.of(15, 15);
    ZoneId ZONE_ID = ZoneId.systemDefault();

}
