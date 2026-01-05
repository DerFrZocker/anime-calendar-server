package de.derfrzocker.anime.calendar.core.integration;

import static org.junit.jupiter.api.Assertions.*;
import de.derfrzocker.anime.calendar.core.exception.InvalidValueFormatException;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class IntegrationIdTest {

    static Stream<String> validValues() {
        return Stream.of("anidb", "crunchyroll", "myanimelist", "syoboi");
    }

    static Stream<String> invalidValues() {
        return Stream.of(
                "abcdefghijkl",
                "abcdefghijklmno",
                "",
                "a",
                "ay",
                "Awsxedcrfv",
                "123",
                "a0sxedcrfv",
                "afsxe$crfv",
                "afsxehcrf?",
                "afsxehcrfH",
                "afsXe$C^Fh");
    }

    // <editor-fold desc="<init>" defaultstate="collapsed">
    @Test
    void init_NullValue() {
        assertThrows(
                NullPointerException.class,
                () -> new IntegrationId(null),
                "Null input should throw an exception.");
    }

    @ParameterizedTest
    @MethodSource("invalidValues")
    void init_Invalid(String input) {
        assertThrows(
                InvalidValueFormatException.class,
                () -> new IntegrationId(input),
                "An invalid input should throw an exception.");
    }

    @ParameterizedTest
    @MethodSource("validValues")
    void init(String input) {
        IntegrationId actual = assertDoesNotThrow(
                () -> new IntegrationId(input),
                "A valid input should not throw an exception.");

        assertEquals(input, actual.raw(), "The raw value should be the same as the input value.");
    }
    // </editor-fold>

    // <editor-fold desc="#of(String)" defaultstate="collapsed">
    @Test
    void of_NullValue() {
        assertThrows(NullPointerException.class, () -> IntegrationId.of(null), "Null input should throw an exception.");
    }

    @ParameterizedTest
    @MethodSource("invalidValues")
    void of_Invalid(String input) {
        assertThrows(
                InvalidValueFormatException.class,
                () -> IntegrationId.of(input),
                "An invalid input should throw an exception.");
    }

    @ParameterizedTest
    @MethodSource("validValues")
    void of(String input) {
        IntegrationId actual = assertDoesNotThrow(
                () -> IntegrationId.of(input),
                "A valid input should not throw an exception.");

        assertEquals(input, actual.raw(), "The raw value should be the same as the input value.");
    }
    // </editor-fold>

    // <editor-fold desc="#of(String, Supplier)" defaultstate="collapsed">
    @Test
    void of_WithSupplier_NullValue() {
        assertThrows(
                NullPointerException.class,
                () -> IntegrationId.of(null, RuntimeException::new),
                "Null input should throw an exception.");
    }

    @ParameterizedTest
    @MethodSource("invalidValues")
    void of_WithSupplier_Invalid(String input) {
        assertThrows(
                RuntimeException.class,
                () -> IntegrationId.of(input, RuntimeException::new),
                "An invalid input should throw an exception.");
    }

    @ParameterizedTest
    @MethodSource("validValues")
    void of_WithSupplier(String input) {
        IntegrationId actual = assertDoesNotThrow(
                () -> IntegrationId.of(input, RuntimeException::new),
                "A valid input should not throw an exception.");

        assertEquals(input, actual.raw(), "The raw value should be the same as the input value.");
    }
    // </editor-fold>
}
