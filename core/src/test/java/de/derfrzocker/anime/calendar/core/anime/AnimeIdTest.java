package de.derfrzocker.anime.calendar.core.anime;

import static org.junit.jupiter.api.Assertions.*;
import de.derfrzocker.anime.calendar.core.exception.InvalidValueFormatException;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class AnimeIdTest {

    static Stream<String> validValues() {
        return Stream.of("AQAYWSXEDC", "AQA1W2XE2C", "A1A1W2HE2C");
    }

    static Stream<String> invalidValues() {
        return Stream.of(
                "AQAYWSXEDCR",
                "AMJUNHZBGTVFRCDE",
                "",
                "A",
                "AY",
                "AQAYEDCRF",
                "aWSXEDCRFV",
                "DWSXEDCRFV",
                "AOSXEDCRFV",
                "A0SXEDCRFV",
                "AFSXE$CRFV",
                "AFSXEHCRF?",
                "AFSXEHCRFh",
                "AFSxE$C^Fh");
    }

    // <editor-fold desc="<init>" defaultstate="collapsed">
    @Test
    void init_NullValue() {
        assertThrows(NullPointerException.class, () -> new AnimeId(null), "Null input should throw an exception.");
    }

    @ParameterizedTest
    @MethodSource("invalidValues")
    void init_Invalid(String input) {
        assertThrows(
                InvalidValueFormatException.class,
                () -> new AnimeId(input),
                "An invalid input should throw an exception.");
    }

    @ParameterizedTest
    @MethodSource("validValues")
    void init(String input) {
        AnimeId actual = assertDoesNotThrow(() -> new AnimeId(input), "A valid input should not throw an exception.");

        assertEquals(input, actual.raw(), "The raw value should be the same as the input value.");
    }
    // </editor-fold>

    // <editor-fold desc="#of(String)" defaultstate="collapsed">
    @Test
    void of_NullValue() {
        assertThrows(NullPointerException.class, () -> AnimeId.of(null), "Null input should throw an exception.");
    }

    @ParameterizedTest
    @MethodSource("invalidValues")
    void of_Invalid(String input) {
        assertThrows(
                InvalidValueFormatException.class,
                () -> AnimeId.of(input),
                "An invalid input should throw an exception.");
    }

    @ParameterizedTest
    @MethodSource("validValues")
    void of(String input) {
        AnimeId actual = assertDoesNotThrow(() -> AnimeId.of(input), "A valid input should not throw an exception.");

        assertEquals(input, actual.raw(), "The raw value should be the same as the input value.");
    }
    // </editor-fold>

    // <editor-fold desc="#of(String, Supplier)" defaultstate="collapsed">
    @Test
    void of_WithSupplier_NullValue() {
        assertThrows(
                NullPointerException.class,
                () -> AnimeId.of(null, RuntimeException::new),
                "Null input should throw an exception.");
    }

    @ParameterizedTest
    @MethodSource("invalidValues")
    void of_WithSupplier_Invalid(String input) {
        assertThrows(
                RuntimeException.class,
                () -> AnimeId.of(input, RuntimeException::new),
                "An invalid input should throw an exception.");
    }

    @ParameterizedTest
    @MethodSource("validValues")
    void of_WithSupplier(String input) {
        AnimeId actual = assertDoesNotThrow(
                () -> AnimeId.of(input, RuntimeException::new),
                "A valid input should not throw an exception.");

        assertEquals(input, actual.raw(), "The raw value should be the same as the input value.");
    }
    // </editor-fold>
}
