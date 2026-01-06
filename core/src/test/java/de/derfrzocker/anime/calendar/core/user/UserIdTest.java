package de.derfrzocker.anime.calendar.core.user;

import static org.junit.jupiter.api.Assertions.*;
import de.derfrzocker.anime.calendar.core.exception.InvalidValueFormatException;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class UserIdTest {

    static Stream<String> validValues() {
        return Stream.of("UQAYWSXEDC", "UQA1W2XE2C", "U1A1W2HE2C");
    }

    static Stream<String> invalidValues() {
        return Stream.of(
                "UQAYWSXEDCR",
                "UMJUNHZBGTVFRCDE",
                "",
                "U",
                "AY",
                "UQAYEDCRF",
                "uWSXEDCRFV",
                "DWSXEDCRFV",
                "UOSXEDCRFV",
                "U0SXEDCRFV",
                "UFSXE$CRFV",
                "UFSXEHCRF?",
                "UFSXEHCRFh",
                "UFSxE$C^Fh");
    }

    // <editor-fold desc="<init>" defaultstate="collapsed">
    @Test
    void init_NullValue() {
        assertThrows(NullPointerException.class, () -> new UserId(null), "Null input should throw an exception.");
    }

    @ParameterizedTest
    @MethodSource("invalidValues")
    void init_Invalid(String input) {
        assertThrows(
                InvalidValueFormatException.class,
                () -> new UserId(input),
                "An invalid input should throw an exception.");
    }

    @ParameterizedTest
    @MethodSource("validValues")
    void init(String input) {
        UserId actual = assertDoesNotThrow(() -> new UserId(input), "A valid input should not throw an exception.");

        assertEquals(input, actual.raw(), "The raw value should be the same as the input value.");
    }
    // </editor-fold>

    // <editor-fold desc="#of(String)" defaultstate="collapsed">
    @Test
    void of_NullValue() {
        assertThrows(NullPointerException.class, () -> UserId.of(null), "Null input should throw an exception.");
    }

    @ParameterizedTest
    @MethodSource("invalidValues")
    void of_Invalid(String input) {
        assertThrows(
                InvalidValueFormatException.class,
                () -> UserId.of(input),
                "An invalid input should throw an exception.");
    }

    @ParameterizedTest
    @MethodSource("validValues")
    void of(String input) {
        UserId actual = assertDoesNotThrow(() -> UserId.of(input), "A valid input should not throw an exception.");

        assertEquals(input, actual.raw(), "The raw value should be the same as the input value.");
    }
    // </editor-fold>

    // <editor-fold desc="#of(String, Supplier)" defaultstate="collapsed">
    @Test
    void of_WithSupplier_NullValue() {
        assertThrows(
                NullPointerException.class,
                () -> UserId.of(null, RuntimeException::new),
                "Null input should throw an exception.");
    }

    @ParameterizedTest
    @MethodSource("invalidValues")
    void of_WithSupplier_Invalid(String input) {
        assertThrows(
                RuntimeException.class,
                () -> UserId.of(input, RuntimeException::new),
                "An invalid input should throw an exception.");
    }

    @ParameterizedTest
    @MethodSource("validValues")
    void of_WithSupplier(String input) {
        UserId actual = assertDoesNotThrow(
                () -> UserId.of(input, RuntimeException::new),
                "A valid input should not throw an exception.");

        assertEquals(input, actual.raw(), "The raw value should be the same as the input value.");
    }
    // </editor-fold>
}
