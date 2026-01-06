package de.derfrzocker.anime.calendar.core.calendar;

import static org.junit.jupiter.api.Assertions.*;
import de.derfrzocker.anime.calendar.core.exception.InvalidValueFormatException;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class CalendarKeyTest {

    static Stream<String> validValues() {
        return Stream.of(
                "CQAYWSXEDCKA1B2CASD89ASD67HJK345I9ASDJKH",
                "CQA1W2XE2CK78H8ASDF87H23HASDF87H2398SDFA",
                "C1A1W2HE2CKKJH345KBNSDF89H234KJH189SDFSK");
    }

    static Stream<String> invalidValues() {
        return Stream.of(
                "CQAYWSXEDKA1B2CASD89ASD67HJK345I9ASDJKHA",
                "CMJUNHZBGTKKJH345KBNSDF89H234KJH189SDFSKVFRCDE",
                "",
                "C",
                "CY",
                "CQAYEDCRFAKKJH345KBNSDF89H234KJH189SDFS",
                "cWSXEDCRFVKKJH345KBNSDF89H234KJH189SDF",
                "CWSXEDCRFVkKJH345KBNSDF89H234KJH189SDF",
                "CWSXEDCRFVDKJH345KBNSDF89H234KJH189SDF",
                "DWSXEDCRFVKKJH345KBNSDF89H234KJH189SDF",
                "COSXEDCRFVKKJH345KBNSDF89H234KJH189SDF",
                "C0SXEDCRFVKKJH345KBNSDF89H234KJH189SDF",
                "CFSXE$CRFVKKJH345KBNSDF89H234KJH189SDF",
                "CFSXEHCRF?KKJH345KBNSDF89H234KJH189SDF",
                "CFSXEHCRFhKKJH345KBNSDF89H234KJH189SDF",
                "CFSxE$C^FhKKJH345KBNSDF8=H&A4KJH189SDF");
    }

    // <editor-fold desc="<init>" defaultstate="collapsed">
    @Test
    void init_NullValue() {
        assertThrows(NullPointerException.class, () -> new CalendarKey(null), "Null input should throw an exception.");
    }

    @ParameterizedTest
    @MethodSource("invalidValues")
    void init_Invalid(String input) {
        assertThrows(
                InvalidValueFormatException.class,
                () -> new CalendarKey(input),
                "An invalid input should throw an exception.");
    }

    @ParameterizedTest
    @MethodSource("validValues")
    void init(String input) {
        CalendarKey actual = assertDoesNotThrow(
                () -> new CalendarKey(input),
                "A valid input should not throw an exception.");

        assertEquals(input, actual.raw(), "The raw value should be the same as the input value.");
    }
    // </editor-fold>

    // <editor-fold desc="#of(String)" defaultstate="collapsed">
    @Test
    void of_NullValue() {
        assertThrows(NullPointerException.class, () -> CalendarKey.of(null), "Null input should throw an exception.");
    }

    @ParameterizedTest
    @MethodSource("invalidValues")
    void of_Invalid(String input) {
        assertThrows(
                InvalidValueFormatException.class,
                () -> CalendarKey.of(input),
                "An invalid input should throw an exception.");
    }

    @ParameterizedTest
    @MethodSource("validValues")
    void of(String input) {
        CalendarKey actual = assertDoesNotThrow(
                () -> CalendarKey.of(input),
                "A valid input should not throw an exception.");

        assertEquals(input, actual.raw(), "The raw value should be the same as the input value.");
    }
    // </editor-fold>

    // <editor-fold desc="#of(String, Supplier)" defaultstate="collapsed">
    @Test
    void of_WithSupplier_NullValue() {
        assertThrows(
                NullPointerException.class,
                () -> CalendarKey.of(null, RuntimeException::new),
                "Null input should throw an exception.");
    }

    @ParameterizedTest
    @MethodSource("invalidValues")
    void of_WithSupplier_Invalid(String input) {
        assertThrows(
                RuntimeException.class,
                () -> CalendarKey.of(input, RuntimeException::new),
                "An invalid input should throw an exception.");
    }

    @ParameterizedTest
    @MethodSource("validValues")
    void of_WithSupplier(String input) {
        CalendarKey actual = assertDoesNotThrow(
                () -> CalendarKey.of(input, RuntimeException::new),
                "A valid input should not throw an exception.");

        assertEquals(input, actual.raw(), "The raw value should be the same as the input value.");
    }
    // </editor-fold>

    // <editor-fold desc="#calendarId()" defaultstate="collapsed">
    @Test
    void testCalendarId() {
        CalendarKey calendarKey = CalendarKey.of("CBCDEFGHIJKLMNPQRSTU98AHSD9AHSD98HASDASD");

        assertEquals(new CalendarId("CBCDEFGHIJ"), calendarKey.calendarId(), "Calendar id should be correct.");
    }
    // </editor-fold>
}
