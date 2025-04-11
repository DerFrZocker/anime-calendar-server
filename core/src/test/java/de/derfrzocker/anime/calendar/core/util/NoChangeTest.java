package de.derfrzocker.anime.calendar.core.util;

import static org.junit.jupiter.api.Assertions.*;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class NoChangeTest {

    public static Stream<Arguments> testApply() {
        return Stream.of(Arguments.of(null, null), Arguments.of("Fox", "Fox"));
    }

    @ParameterizedTest
    @MethodSource(value = "testApply")
    void testApply(String expected, String input) {
        assertEquals(expected, Change.nothing().apply(input), "Change should return the input value.");
    }
}
