package de.derfrzocker.anime.calendar.core.layer;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class LayerParserKeyTest {

    // <editor-fold desc="<init>" defaultstate="collapsed">
    @Test
    void test_NullValue() {
        assertThrows(NullPointerException.class,
                     () -> new LayerParserKey(null),
                     "Null input should throw an exception.");
    }

    @Test
    void test() {
        assertDoesNotThrow(() -> new LayerParserKey("Cat"), "Not null input should not throw an exception.");
    }
    // </editor-fold>
}
