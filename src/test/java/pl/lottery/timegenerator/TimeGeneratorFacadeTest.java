package pl.lottery.timegenerator;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.*;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class TimeGeneratorFacadeTest {

    private final LocalDate sampleDateTests = LocalDate.of(2022, 8, 8);
    private final LocalTime sampleTimeTests = LocalTime.of(10, 10);
    private final DayOfWeek drawDayOfWeek = DayOfWeek.FRIDAY;
    private final LocalTime drawTime = LocalTime.of(12, 10);
    private final Duration expirationInDays = Duration.ofDays(365 * 2);
    private final ZoneId zoneId = ZoneId.systemDefault();
    private final TimeConfigurable timeConfigurator = new TimePropertyConfigTests(drawDayOfWeek, drawTime, expirationInDays);
    private final LocalDateTime expectedCurrentTime = LocalDateTime.of(sampleDateTests, sampleTimeTests);
    private final AdjustableClock sampleClockForTests = AdjustableClock.ofLocalDateAndLocalTime(sampleDateTests, sampleTimeTests, zoneId);
    private final TimeGeneratorConfiguration timeGeneratorConfiguration = new TimeGeneratorConfiguration();
    private final TimeGeneratorFacade timeGeneratorFacade = timeGeneratorConfiguration.createForTest(sampleClockForTests, timeConfigurator);

    @AfterEach
    void tearDown() {
        sampleClockForTests.setClockToLocalDateTime(expectedCurrentTime);
    }

    @Test
    @DisplayName("should return relative current time when sample clock is provided")
    void getCurrentDateAndTime_givenSampleTime_returnsDateEqualsSampleTime() {
        // given
        LocalDate sampleDate = LocalDate.of(2022, 8, 15);
        LocalTime sampleTime = LocalTime.of(22, 15);
        sampleClockForTests.setClockToLocalDate(sampleDate);
        sampleClockForTests.setClockToLocalTime(sampleTime);

        // when
        LocalDateTime actualCurrentTime = timeGeneratorFacade.getCurrentDateAndTime();

        // then
        LocalDateTime expectedLocalDateTime = LocalDateTime.of(sampleDate, sampleTime);
        assertThat(actualCurrentTime).isEqualTo(expectedLocalDateTime);

    }

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
        sampleClockForTests.setClockToLocalTime(LocalTime.of(8, 0));
        sampleClockForTests.setClockToLocalDate(sampleDate);

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
        sampleClockForTests.setClockToLocalDate(LocalDate.of(2022, 8, 12));
        sampleClockForTests.setClockToLocalTime(drawTime.plusHours(1));

        // when
        LocalDateTime actualDrawTime = timeGeneratorFacade.getDrawDateAndTime();

        // then
        LocalDateTime expectedDrawDateTime = LocalDateTime.of(LocalDate.of(2022, 8, 19), LocalTime.of(12, 10));
        assertThat(actualDrawTime).isEqualTo(expectedDrawDateTime);

    }

    @Test
    @DisplayName("should return expiration date based on specified current time")
    void getExpirationDateAndTime_givenDrawDateAndExpiration_returnsExpirationDate() {
        // given
        // when
        LocalDateTime actualExpirationDateTime = timeGeneratorFacade.getExpirationDateAndTime();

        // then
        LocalDateTime drawDateTime = timeGeneratorFacade.getDrawDateAndTime();
        LocalDateTime expectedExpirationDateTime = drawDateTime.plusDays(expirationInDays.toDays());
        assertThat(actualExpirationDateTime).isEqualTo(expectedExpirationDateTime);

    }

}

