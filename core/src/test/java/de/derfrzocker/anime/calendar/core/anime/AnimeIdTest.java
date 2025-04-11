package de.derfrzocker.anime.calendar.core.anime;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class AnimeIdTest {

    // <editor-fold desc="<init>" defaultstate="collapsed">
    @Test
    void test_NullValue() {
        assertThrows(NullPointerException.class, () -> new AnimeId(null), "Null input should throw an exception.");
    }

    @Test
    void test() {
        assertDoesNotThrow(() -> new AnimeId("Cat"), "Not null input should not throw an exception.");
    }
    // </editor-fold>
}
