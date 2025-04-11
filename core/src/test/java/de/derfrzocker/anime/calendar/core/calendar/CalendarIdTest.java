package de.derfrzocker.anime.calendar.core.calendar;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class CalendarIdTest {

    // <editor-fold desc="<init>" defaultstate="collapsed">
    @Test
    void test_NullValue() {
        assertThrows(NullPointerException.class, () -> new CalendarId(null), "Null input should throw an exception.");
    }

    @Test
    void test() {
        assertDoesNotThrow(() -> new CalendarId("Cat"), "Not null input should not throw an exception.");
    }
    // </editor-fold>
}
