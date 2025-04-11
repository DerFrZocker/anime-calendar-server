package de.derfrzocker.anime.calendar.core.util;

import static org.junit.jupiter.api.Assertions.*;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class NullChangeTest {

    public static Stream<Arguments> testApply() {
        return Stream.of(Arguments.of(new Object[]{null}), Arguments.of("Fox"));
    }

    @ParameterizedTest
    @MethodSource(value = "testApply")
    void testApply(String input) {
        assertNull(Change.toNull().apply(input), "It should always return null.");
    }
}
