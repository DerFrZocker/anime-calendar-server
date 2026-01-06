package de.derfrzocker.anime.calendar.core.calendar;

import static org.junit.jupiter.api.Assertions.*;
import de.derfrzocker.anime.calendar.core.exception.InvalidValueFormatException;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class CalendarIdTest {

    static Stream<String> validValues() {
        return Stream.of("CQAYWSXEDC", "CQA1W2XE2C", "C1A1W2HE2C");
    }

    static Stream<String> invalidValues() {
        return Stream.of(
                "CQAYWSXEDCR",
                "CMJUNHZBGTVFRCDE",
                "",
                "C",
                "CY",
                "CQAYEDCRF",
                "cWSXEDCRFV",
                "DWSXEDCRFV",
                "COSXEDCRFV",
                "C0SXEDCRFV",
                "CFSXE$CRFV",
                "CFSXEHCRF?",
                "CFSXEHCRFh",
                "CFSxE$C^Fh");
    }

    // <editor-fold desc="<init>" defaultstate="collapsed">
    @Test
    void init_NullValue() {
        assertThrows(NullPointerException.class, () -> new CalendarId(null), "Null input should throw an exception.");
    }

    @ParameterizedTest
    @MethodSource("invalidValues")
    void init_Invalid(String input) {
        assertThrows(
                InvalidValueFormatException.class,
                () -> new CalendarId(input),
                "An invalid input should throw an exception.");
    }

    @ParameterizedTest
    @MethodSource("validValues")
    void init(String input) {
        CalendarId actual = assertDoesNotThrow(
                () -> new CalendarId(input),
                "A valid input should not throw an exception.");

        assertEquals(input, actual.raw(), "The raw value should be the same as the input value.");
    }
    // </editor-fold>

    // <editor-fold desc="#of(String)" defaultstate="collapsed">
    @Test
    void of_NullValue() {
        assertThrows(NullPointerException.class, () -> CalendarId.of(null), "Null input should throw an exception.");
    }

    @ParameterizedTest
    @MethodSource("invalidValues")
    void of_Invalid(String input) {
        assertThrows(
                InvalidValueFormatException.class,
                () -> CalendarId.of(input),
                "An invalid input should throw an exception.");
    }

    @ParameterizedTest
    @MethodSource("validValues")
    void of(String input) {
        CalendarId actual = assertDoesNotThrow(
                () -> CalendarId.of(input),
                "A valid input should not throw an exception.");

        assertEquals(input, actual.raw(), "The raw value should be the same as the input value.");
    }
    // </editor-fold>

    // <editor-fold desc="#of(String, Supplier)" defaultstate="collapsed">
    @Test
    void of_WithSupplier_NullValue() {
        assertThrows(
                NullPointerException.class,
                () -> CalendarId.of(null, RuntimeException::new),
                "Null input should throw an exception.");
    }

    @ParameterizedTest
    @MethodSource("invalidValues")
    void of_WithSupplier_Invalid(String input) {
        assertThrows(
                RuntimeException.class,
                () -> CalendarId.of(input, RuntimeException::new),
                "An invalid input should throw an exception.");
    }

    @ParameterizedTest
    @MethodSource("validValues")
    void of_WithSupplier(String input) {
        CalendarId actual = assertDoesNotThrow(
                () -> CalendarId.of(input, RuntimeException::new),
                "A valid input should not throw an exception.");

        assertEquals(input, actual.raw(), "The raw value should be the same as the input value.");
    }
    // </editor-fold>
}
