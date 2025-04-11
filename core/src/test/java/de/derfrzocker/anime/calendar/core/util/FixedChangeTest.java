package de.derfrzocker.anime.calendar.core.util;

import static org.junit.jupiter.api.Assertions.*;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class FixedChangeTest {

    public static Stream<Arguments> testApply() {
        return Stream.of(Arguments.of(null, null, null),
                         Arguments.of(null, null, "Fox"),
                         Arguments.of("Fox", "Fox", null),
                         Arguments.of("Fox", "Fox", "Tiger"));
    }

    @ParameterizedTest
    @MethodSource(value = "testApply")
    void testApply(String expected, String to, String input) {
        assertEquals(expected, Change.to(to).apply(input), "Change should return the 'to' value.");
    }
}
