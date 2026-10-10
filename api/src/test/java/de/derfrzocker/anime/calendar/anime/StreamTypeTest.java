package de.derfrzocker.anime.calendar.anime;

import static org.junit.jupiter.api.Assertions.*;
import de.derfrzocker.anime.calendar.exception.InvalidValueFormatException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class StreamTypeTest {

    @Test
    void init_Null() {
        assertThrows(NullPointerException.class, () -> new StreamType(null));
    }

    @ValueSource(strings = {"", "SUB", "sUb", "123", "!$§", "123", "abc"})
    @ParameterizedTest
    void init_Invalid(String raw) {
        assertThrows(InvalidValueFormatException.class, () -> new StreamType(raw));
    }

    @ValueSource(strings = {"org", "sub", "dub"})
    @ParameterizedTest
    void init(String raw) {
        StreamType result = assertDoesNotThrow(() -> new StreamType(raw));

        assertEquals(raw, result.raw());
    }
}
