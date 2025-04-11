package de.derfrzocker.anime.calendar.core.user;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class UserIdTest {

    // <editor-fold desc="<init>" defaultstate="collapsed">
    @Test
    void test_NullValue() {
        assertThrows(NullPointerException.class, () -> new UserId(null), "Null input should throw an exception.");
    }

    @Test
    void test() {
        assertDoesNotThrow(() -> new UserId("Cat"), "Not null input should not throw an exception.");
    }
    // </editor-fold>
}
