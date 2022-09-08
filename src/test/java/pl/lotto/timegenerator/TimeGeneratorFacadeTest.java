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
    private final LocalDate sampleDateTests = LocalDate.of(2022, 8, 12);
    private final LocalTime sampleTimeTests = LocalTime.of(12, 11);
    private final LocalDateTime expectedCurrentTime = LocalDateTime.of(sampleDateTests, sampleTimeTests);
    private final Clock sampleClockForTests = sampleClock(sampleDateTests, sampleTimeTests);
    private final DayOfWeek drawDayOfWeek = DayOfWeek.FRIDAY;
    private final LocalTime drawTime = LocalTime.of(12, 10);
    private final Duration expirationInDays = Duration.ofDays(365*2);
    private final TimeGeneratorFacade timeGeneratorFacadeForTests = timeGeneratorConfiguration.createForTest(sampleClockForTests,drawDayOfWeek,drawTime,expirationInDays);

    static Stream<Arguments> inputExpectedDrawsDates() {
        return Stream.of(
                //Arguments.of(current point in time, expected draw day of month)
                Arguments.of(LocalDate.of(2022, 8, 8), LocalDate.of(2022, 8, 12)),
                Arguments.of(LocalDate.of(2022, 8, 31), LocalDate.of(2022, 9, 2)),
                Arguments.of(LocalDate.of(2022, 8, 20), LocalDate.of(2022, 8, 26)),
                Arguments.of(LocalDate.of(2022, 8, 12), LocalDate.of(2022, 8, 12))
        );
    }

    @ParameterizedTest
    @MethodSource("inputExpectedDrawsDates")
    @DisplayName("should return draw date when date before, at or after is provided")
    void getDrawDateAndTime_givenPointInTime_returnsDrawDate(LocalDate sampleDate, LocalDate expectedDrawDate) {
        // given
        LocalTime sampleTime = LocalTime.of(8, 0);
        Clock clockForTest = sampleClock(sampleDate, sampleTime);
        TimeGeneratorFacade timeGeneratorFacade = timeGeneratorConfiguration.createForTest(clockForTest,drawDayOfWeek,drawTime,expirationInDays);

        // when
        LocalDateTime actualDrawDateTime = timeGeneratorFacade.getDrawDateAndTime();
        LocalDate actualDrawDate = actualDrawDateTime.toLocalDate();

        // then
        assertThat(actualDrawDate).isEqualTo(expectedDrawDate);
    }

    @Test
    @DisplayName("should return week later draw date when current date is the same as draw date, but current time is later than draw time")
    void getDrawDateAndTime_givenDayOfWeekTheSameAsDrawDayTimeAfterDrawTime_returnsNextDrawDate() {
        // given
        LocalDate expectedDrawDate = LocalDate.of(2022, 8, 19);
        LocalTime expectedDrawTime = LocalTime.of(12, 10);
        // TODO test will fail if draw day of week or time in configuration will change
        LocalDateTime expectedDrawDateTime = LocalDateTime.of(expectedDrawDate, expectedDrawTime);

        // when
        LocalDateTime actualDrawTime = timeGeneratorFacadeForTests.getDrawDateAndTime();

        // then
        assertThat(expectedDrawDateTime).isEqualTo(actualDrawTime);

    }

    @Test
    @DisplayName("should return relative current time when sample clock is provided")
    void getCurrentDateAndTime_givenSampleTime_returnsDateEqualsSampleTime() {
        // when
        LocalDateTime actualCurrentTime = timeGeneratorFacadeForTests.getCurrentDateAndTime();

        // then
        assertThat(actualCurrentTime).isEqualTo(expectedCurrentTime);

    }

    @Test
    @DisplayName("should return expiration date")
    void getExpirationDateAndTime_givenDrawDateAndExpiration_returnsExpirationDate() {
        // given
        LocalDateTime drawDateTime = timeGeneratorFacadeForTests.getDrawDateAndTime();
        Duration expirationInDays = Duration.ofDays(365 * 2); //TODO test will fail if configuration duration is changed
        LocalDateTime expectedExpirationDateTime = drawDateTime.plusDays(expirationInDays.toDays());

        // when
        LocalDateTime actualExpirationDateTime = timeGeneratorFacadeForTests.getExpirationDateAndTime();

        // then
        assertThat(actualExpirationDateTime).isEqualTo(expectedExpirationDateTime);

    }

}

