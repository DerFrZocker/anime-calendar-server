package de.derfrzocker.anime.calendar.core.season;

import static org.junit.jupiter.api.Assertions.*;
import java.time.Month;
import java.time.Year;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class SeasonTest {

    public static Stream<Arguments> data() {
        return Stream.of(
                Arguments.of(Season.WINTER, Month.JANUARY),
                Arguments.of(Season.WINTER, Month.FEBRUARY),
                Arguments.of(Season.WINTER, Month.MARCH),
                Arguments.of(Season.SPRING, Month.APRIL),
                Arguments.of(Season.SPRING, Month.MAY),
                Arguments.of(Season.SPRING, Month.JUNE),
                Arguments.of(Season.SUMMER, Month.JULY),
                Arguments.of(Season.SUMMER, Month.AUGUST),
                Arguments.of(Season.SUMMER, Month.SEPTEMBER),
                Arguments.of(Season.FALL, Month.OCTOBER),
                Arguments.of(Season.FALL, Month.NOVEMBER),
                Arguments.of(Season.FALL, Month.DECEMBER)
        );
    }

    public static Stream<Arguments> dataNextSeason() {
        return Stream.of(
                Arguments.of(Season.SPRING, Season.WINTER),
                Arguments.of(Season.SUMMER, Season.SPRING),
                Arguments.of(Season.FALL, Season.SUMMER),
                Arguments.of(Season.WINTER, Season.FALL)
        );
    }

    public static Stream<Arguments> dataPreviousSeason() {
        return Stream.of(
                Arguments.of(Season.FALL, Season.WINTER),
                Arguments.of(Season.WINTER, Season.SPRING),
                Arguments.of(Season.SPRING, Season.SUMMER),
                Arguments.of(Season.SUMMER, Season.FALL)
        );
    }

    public static Stream<Arguments> dataNextYear() {
        return Stream.of(
                Arguments.of(Year.of(2020), Season.WINTER, Year.of(2020)),
                Arguments.of(Year.of(2020), Season.SPRING, Year.of(2020)),
                Arguments.of(Year.of(2020), Season.SUMMER, Year.of(2020)),
                Arguments.of(Year.of(2021), Season.FALL, Year.of(2020))
        );
    }

    public static Stream<Arguments> dataPreviousYear() {
        return Stream.of(
                Arguments.of(Year.of(2019), Season.WINTER, Year.of(2020)),
                Arguments.of(Year.of(2020), Season.SPRING, Year.of(2020)),
                Arguments.of(Year.of(2020), Season.SUMMER, Year.of(2020)),
                Arguments.of(Year.of(2020), Season.FALL, Year.of(2020))
        );
    }

    @ParameterizedTest
    @MethodSource("data")
    public void testCorrectSeason(Season expectedSeason, Month toTest) {
        assertSame(expectedSeason, Season.getSeason(toTest));
    }

    @ParameterizedTest
    @MethodSource("dataNextSeason")
    public void testCorrectNextSeason(Season expectedSeason, Season toTest) {
        assertSame(expectedSeason, toTest.nextSeason());
    }

    @ParameterizedTest
    @MethodSource("dataPreviousSeason")
    public void testCorrectPreviousSeason(Season expectedSeason, Season toTest) {
        assertSame(expectedSeason, toTest.previousSeason());
    }

    @ParameterizedTest
    @MethodSource("dataNextYear")
    public void testCorrectNextYear(Year expectedYear, Season toTest, Year current) {
        assertEquals(expectedYear, toTest.nextYear(current));
    }

    @ParameterizedTest
    @MethodSource("dataPreviousYear")
    public void testCorrectPreviousSeason(Year expectedYear, Season toTest, Year current) {
        assertEquals(expectedYear, toTest.previousYear(current));
    }
}
