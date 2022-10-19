package pl.lotto;

import java.time.LocalDateTime;
import java.util.List;

public interface TestConstants {

    List<Integer> WINNERS_TYPED_NUMBERS = List.of(1, 2, 3, 4, 5, 6);
    List<Integer> LOSERS_TYPED_NUMBERS = List.of(20, 30, 40, 50, 60, 70);
    LocalDateTime DRAW_DATE = LocalDateTime.of(2022, 8, 13, 12, 0);

}
