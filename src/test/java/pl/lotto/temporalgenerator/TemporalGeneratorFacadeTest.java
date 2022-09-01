package pl.lotto.temporalgenerator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.*;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class TemporalGeneratorFacadeTest implements SampleClock {

    public static Stream<Arguments> inputDatesExpectedDraws() {
        return Stream.of(
                //Arguments.of(current point in time, expected draw day of month)
                Arguments.of(LocalDate.of(2022, 8, 8), LocalDate.of(2022, 8, 12)),
                Arguments.of(LocalDate.of(2022, 8, 31), LocalDate.of(2022, 9, 2)),
                Arguments.of(LocalDate.of(2022, 8, 20), LocalDate.of(2022, 8, 26)),
                Arguments.of(LocalDate.of(2022, 8, 12), LocalDate.of(2022, 8, 12))
        );
    }

    @ParameterizedTest
    @MethodSource("inputDatesExpectedDraws")
    @DisplayName("gets correct draw date for specified point in time before or after expected draw day of week ")
    void getCurrentDrawDate_givenPointInTime_returnsDrawDate(LocalDate givenDate, LocalDate expectedDrawDate) {
        // given
        LocalTime timeForTest = LocalTime.of(8, 0);
        Clock clockForTest = sampleClock(givenDate, timeForTest);
        DayOfWeek expectedDrawDayOfWeek = DayOfWeek.FRIDAY;
        LocalTime expectedDrawTime = LocalTime.of(12, 10);
        TemporalGeneratorConfiguration drawDateConfig = new TemporalGeneratorConfiguration();
        Duration expirationDays = Duration.ofDays(365*2);
        TemporalGeneratorFacade temporalGeneratorFacade = drawDateConfig.createForTest(expectedDrawDayOfWeek, expectedDrawTime, expirationDays, clockForTest);

        // when
        LocalDateTime actualDrawDateTime = temporalGeneratorFacade.getDrawDateAndTime();
        LocalDate actualDrawDate = actualDrawDateTime.toLocalDate();

        // then
        assertThat(actualDrawDate).isEqualTo(expectedDrawDate);
    }

}

