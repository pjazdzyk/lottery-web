package com.synerset.lottery.lotterygenerator;

import java.time.LocalDateTime;
import java.util.List;

public interface TestConstants {

    List<Integer> WINNING_NUMBERS = List.of(1, 2, 3, 4, 5, 6);
    LocalDateTime DRAW_DATE = LocalDateTime.of(2022, 8, 13, 12, 0);

}
