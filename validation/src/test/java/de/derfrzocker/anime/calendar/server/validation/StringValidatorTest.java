package de.derfrzocker.anime.calendar.server.validation;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class StringValidatorTest {

    // <editor-fold desc="StringValidator#isValidLength(String, int)" defaultstate="collapsed">
    @Test
    public void testIsValidLength_InputToShort() {
        assertFalse(StringValidator.isValidLength("SHORT", 10));
    }

    @Test
    public void testIsValidLength_InputToLong() {
        assertFalse(StringValidator.isValidLength("THISISTOLONG", 10));
    }

    @Test
    public void testIsValidLength_InputCorrect() {
        assertTrue(StringValidator.isValidLength("CORRECTSIZ", 10));
    }
    // </editor-fold>

    // <editor-fold desc="StringValidator#isValidStartChar(String, char)" defaultstate="collapsed">
    @Test
    public void testIsValidStartChar_InvalidChar() {
        assertFalse(StringValidator.isValidStartChar("TER", 'A'));
    }

    @Test
    public void testIsValidStartChar_ValidChar() {
        assertTrue(StringValidator.isValidStartChar("TER", 'T'));
    }
    // </editor-fold>

    // <editor-fold desc="StringValidator#isValidChar(String, char, int)" defaultstate="collapsed">
    @Test
    public void testIsValidChar_InvalidChar() {
        assertFalse(StringValidator.isValidChar("TER", 'A', 2));
    }

    @Test
    public void testIsValidChar_PostionHigherThanLength() {
        assertFalse(StringValidator.isValidChar("TER", 'A', 5));
    }

    @Test
    public void testIsValidChar_PostionNegative() {
        assertFalse(StringValidator.isValidChar("TER", 'A', -1));
    }

    @Test
    public void testIsValidChar_ValidChar() {
        assertTrue(StringValidator.isValidChar("TER", 'R', 2));
    }
    // </editor-fold>

    // <editor-fold desc="StringValidator#isValidChars(String)" defaultstate="collapsed">
    @ParameterizedTest
    @ValueSource(strings = {"skdjfgh", "ASDKLJj", "ASKJHDFO", "78kjsd88", "°!°ASDJH", "0ASD"})
    public void testIsValidChars_InvalidChar(String input) {
        assertFalse(StringValidator.isValidChars(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"ASJKDH1231234", "KLJSDFKL", "23451"})
    public void testIsValidChars_ValidChar(String input) {
        assertTrue(StringValidator.isValidChars(input));
    }
    // </editor-fold>
}
