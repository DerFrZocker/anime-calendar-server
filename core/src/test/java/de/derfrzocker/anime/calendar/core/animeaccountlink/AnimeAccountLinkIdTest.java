package de.derfrzocker.anime.calendar.core.animeaccountlink;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class AnimeAccountLinkIdTest {

    // <editor-fold desc="<init>" defaultstate="collapsed">
    @Test
    void test_NullValue() {
        assertThrows(NullPointerException.class,
                     () -> new AnimeAccountLinkId(null),
                     "Null input should throw an exception.");
    }

    @Test
    void test() {
        assertDoesNotThrow(() -> new AnimeAccountLinkId("Cat"), "Not null input should not throw an exception.");
    }
    // </editor-fold>
}
