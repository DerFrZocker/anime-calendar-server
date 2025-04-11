package de.derfrzocker.anime.calendar.core.util;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class WrapperUtilTest {

    // <editor-fold desc="#unwrapSafe(T, Function<T, R>)" defaultstate="collapsed">
    @Test
    void testUnwrapSafe_NullValue() {
        assertNull(WrapperUtil.unwrapSafe(null, v -> fail("Function should not get called for null input.")),
                   "#unwrapSafe should return null on null input.");
    }

    @Test
    void testUnwrapSafe() {
        assertEquals("Cat",
                     WrapperUtil.unwrapSafe("Fox", v -> "Cat"),
                     "#unwrapSafe should return 'Cat' from the Function return.");
    }
    // </editor-fold>

    // <editor-fold desc="#toString(T)" defaultstate="collapsed">
    @Test
    void testToString_NullValue() {
        assertEquals("<null>", WrapperUtil.toString(null), "#toString should return '<null>' on null input.");
    }

    @Test
    void testToString() {
        assertEquals("10", WrapperUtil.toString(10), "#toString should return '10' on 10 input.");
    }
    // </editor-fold>
}
