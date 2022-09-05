package pl.lotto.timegenerator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.*;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class TimeGeneratorFacadeTest implements SampleClock {

    private final TimeGeneratorConfiguration timeGeneratorConfiguration = new TimeGeneratorConfiguration();
    private final DayOfWeek expectedDrawDayOfWeek = DayOfWeek.FRIDAY;
    private final LocalTime expectedDrawTime = LocalTime.of(12, 10);
    private final Duration expirationDays = Duration.ofDays(365 * 2);
    private final LocalDate sampleDateTests = LocalDate.of(2022, 8, 12);
    private final LocalTime sampleTimeTests = LocalTime.of(12, 11);
    private final LocalDateTime expectedCurrentTime = LocalDateTime.of(sampleDateTests, sampleTimeTests);
    private final Clock sampleClockForTests = sampleClock(sampleDateTests, sampleTimeTests);
    private final TimeGeneratorFacade timeGeneratorFacadeForTests = timeGeneratorConfiguration.createForTest(expectedDrawDayOfWeek, expectedDrawTime, expirationDays, sampleClockForTests);


    static Stream<Arguments> inputDatesExpectedDraws() {
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
    void getDrawDateAndTime_givenPointInTime_returnsDrawDate(LocalDate sampleDate, LocalDate expectedDrawDate) {
        // given
        LocalTime sampleTime = LocalTime.of(8, 0);
        Clock clockForTest = sampleClock(sampleDate, sampleTime);
        TimeGeneratorFacade timeGeneratorFacade = timeGeneratorConfiguration.createForTest(expectedDrawDayOfWeek, expectedDrawTime, expirationDays, clockForTest);

        // when
        LocalDateTime actualDrawDateTime = timeGeneratorFacade.getDrawDateAndTime();
        LocalDate actualDrawDate = actualDrawDateTime.toLocalDate();

        // then
        assertThat(actualDrawDate).isEqualTo(expectedDrawDate);
    }

    @Test
    @DisplayName("gets correct draw date if draw day is the same but hour is later than draw time")
    void getDrawDateAndTime_givenDayOfWeekTheSameAsDrawDayTimeAfterDrawTime_returnsNextDrawDate() {
        // given
        LocalDate expectedDrawDate = LocalDate.of(2022, 8, 19);
        LocalDateTime expectedDrawDateTime = LocalDateTime.of(expectedDrawDate, expectedDrawTime);

        // when
        LocalDateTime actualDrawTime = timeGeneratorFacadeForTests.getDrawDateAndTime();

        // then
        assertThat(expectedDrawDateTime).isEqualTo(actualDrawTime);

    }

    @Test
    @DisplayName("gets current time as specified in sample time")
    void getCurrentDateAndTime_givenSampleTime_returnsDateEqualsSampleTime() {
        // when
        LocalDateTime actualCurrentTime = timeGeneratorFacadeForTests.getCurrentDateAndTime();

        // then
        assertThat(actualCurrentTime).isEqualTo(expectedCurrentTime);

    }

    @Test
    @DisplayName("gets correct expiration date based on duration and draw date")
    void getExpirationDateAndTime_givenDrawDateAndExpiration_returnsExpirationDate() {
        // given
        LocalDateTime drawDateTime = timeGeneratorFacadeForTests.getDrawDateAndTime();
        LocalDateTime expectedExpirationDateTime = drawDateTime.plusDays(expirationDays.toDays());

        // when
        LocalDateTime actualExpirationDateTime = timeGeneratorFacadeForTests.getExpirationDateAndTime();

        // then
        assertThat(actualExpirationDateTime).isEqualTo(expectedExpirationDateTime);

    }

}

