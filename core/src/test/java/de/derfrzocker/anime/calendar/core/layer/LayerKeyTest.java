package de.derfrzocker.anime.calendar.core.layer;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class LayerKeyTest {

    // <editor-fold desc="<init>" defaultstate="collapsed">
    @Test
    void test_NullValue() {
        assertThrows(NullPointerException.class, () -> new LayerKey(null), "Null input should throw an exception.");
    }

    @Test
    void test() {
        assertDoesNotThrow(() -> new LayerKey("Cat"), "Not null input should not throw an exception.");
    }
    // </editor-fold>
}
