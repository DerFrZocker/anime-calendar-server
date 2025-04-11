package de.derfrzocker.anime.calendar.core.integration;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class IntegrationIdTest {

    // <editor-fold desc="<init>" defaultstate="collapsed">
    @Test
    void test_NullValue() {
        assertThrows(NullPointerException.class,
                     () -> new IntegrationId(null),
                     "Null input should throw an exception.");
    }

    @Test
    void test() {
        assertDoesNotThrow(() -> new IntegrationId("Cat"), "Not null input should not throw an exception.");
    }
    // </editor-fold>
}
