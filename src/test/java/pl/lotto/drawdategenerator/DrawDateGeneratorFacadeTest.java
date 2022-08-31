package pl.lotto.drawdategenerator;

import java.time.Clock;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.assertj.core.api.Assertions.assertThat;

class DrawDateGeneratorFacadeTest implements SampleClock {

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
        Clock clockForTest = sampleClock(2022, 8, 31);
        DrawDateGeneratorFacade drawDateGeneratorFacade = new DrawDateGeneratorConfiguration().createForTest(clockForTest);
        DayOfWeek expectedDrawDayOfWeek = DayOfWeek.FRIDAY;
        LocalTime expectedDrawTime = LocalTime.of(12, 10);
        LocalDateTime givenPointInTime = LocalDateTime.of(givenDate, LocalTime.of(10, 15));
        drawDateGeneratorFacade.setNewDrawDateAndTime(expectedDrawDayOfWeek, expectedDrawTime);

        // when
        LocalDateTime actualDrawDateTime = drawDateGeneratorFacade.getDrawDate();
        LocalDate actualDrawDate = actualDrawDateTime.toLocalDate();

//        Clock.offset(clockForTest, Duration.ofMinutes(15));

        // then
        assertThat(actualDrawDate).isEqualTo(expectedDrawDate);

//        try (MockedStatic<CurrentTimeGenerator> mockedLocalDateTime = Mockito.mockStatic(CurrentTimeGenerator.class)) {
//            mockedLocalDateTime.when(CurrentTimeGenerator::getCurrentDateAndTime).thenReturn(givenPointInTime);
//            drawDateGeneratorFacade.setNewDrawDateAndTime(expectedDrawDayOfWeek, expectedDrawTime);
//
//            // when
//            LocalDateTime actualDrawDateTime = drawDateGeneratorFacade.getDrawDate();
//            LocalDate actualDrawDate = actualDrawDateTime.toLocalDate();
//
//            // then
//            assertThat(actualDrawDate).isEqualTo(expectedDrawDate);
//
//        }
    }

}

