package de.derfrzocker.anime.calendar.core.notify;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class NotificationActionIdTest {

    // <editor-fold desc="<init>" defaultstate="collapsed">
    @Test
    void test_NullValue() {
        assertThrows(NullPointerException.class,
                     () -> new NotificationActionId(null),
                     "Null input should throw an exception.");
    }

    @Test
    void test() {
        assertDoesNotThrow(() -> new NotificationActionId("Cat"), "Not null input should not throw an exception.");
    }
    // </editor-fold>
}
