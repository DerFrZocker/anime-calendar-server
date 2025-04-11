package de.derfrzocker.anime.calendar.core.calendar;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class CalendarKeyTest {

    // <editor-fold desc="<init>" defaultstate="collapsed">
    @Test
    void test_NullValue() {
        assertThrows(NullPointerException.class, () -> new CalendarKey(null), "Null input should throw an exception.");
    }

    @Test
    void test() {
        assertDoesNotThrow(() -> new CalendarKey("Cat"), "Not null input should not throw an exception.");
    }
    // </editor-fold>

    // <editor-fold desc="#calendarId()" defaultstate="collapsed">
    @Test
    void testCalendarId() {
        CalendarKey calendarKey = new CalendarKey("ABCDEFGHIJKLMNPQRSTU");

        assertEquals(new CalendarId("ABCDEFGHIJ"), calendarKey.calendarId(), "Calendar id should be correct.");
    }
    // </editor-fold>
}
