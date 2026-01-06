package de.derfrzocker.anime.calendar.core.util;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class ValidatorUtilTest {

    // <editor-fold desc="#isValidId(String, int, char)" defaultstate="collapsed">
    @Test
    void isValidId_NullInput() {
        assertThrows(
                NullPointerException.class,
                () -> ValidatorUtil.isValidId(null, 1, 'A'),
                "Null input should throw an exception.");
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
            CABCDEFGHI, 10, C, true
            12345, 5, 1, true
            1A2BC, 5, 1, true
            C0BCDEFGHI, 10, C, false
            COBCDEFGHI, 10, C, false
            CABCDEFGH, 10, C, false
            CABCDEFGHIJ, 10, C, false
            AABCDEFGHI, 10, C, false
            bABCDEFGHI, 10, C, false
            CaBCDEFGHI, 10, C, false
            Cabcdefghi, 10, C, false
            CABCDEFGH!, 10, C, false
            CAB%*ÜFGHI, 10, C, false
            """)
    void isValidId(String input, int length, char prefix, boolean expected) {
        boolean actual = ValidatorUtil.isValidId(input, length, prefix);

        assertEquals(expected, actual, "The validation should return the expected value.");
    }
    // </editor-fold>

    // <editor-fold desc="#isLengthBetween(String, int, int)" defaultstate="collapsed">
    @Test
    void isLengthBetween_NullInput() {
        assertThrows(
                NullPointerException.class,
                () -> ValidatorUtil.isLengthBetween(null, 1, 2),
                "Null input should throw an exception.");
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
            123456, 2, 7, true
            123456, 2, 6, true
            123456, 6, 10, true
            123456, 6, 6, true
            123, 2, 6, true
            123, -2, 6, true
            123, 5, 6, false
            123, 4, 6, false
            123, 6, 6, false
            12345678, 4, 6, false
            1234567, 4, 6, false
            12345678, 6, 6, false
            12345678, 10, 5, false
            """)
    void isLengthBetween(String input, int minInclusive, int maxInclusive, boolean expected) {
        boolean actual = ValidatorUtil.isLengthBetween(input, minInclusive, maxInclusive);

        assertEquals(expected, actual, "The validation should return the expected value.");
    }
    // </editor-fold>

    // <editor-fold desc="#isValidLength(String, int)" defaultstate="collapsed">
    @Test
    void isValidLength_NullInput() {
        assertThrows(
                NullPointerException.class,
                () -> ValidatorUtil.isValidLength(null, 1),
                "Null input should throw an exception.");
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
            123456, 6, true
            1234, 4, true
            123456, -5, false
            123456, 3, false
            123456, 5, false
            123456, 7, false
            123456, 10, false
            """)
    void isValidLength(String input, int length, boolean expected) {
        boolean actual = ValidatorUtil.isValidLength(input, length);

        assertEquals(expected, actual, "The validation should return the expected value.");
    }
    // </editor-fold>

    // <editor-fold desc="#isValidStartChar(String, char)" defaultstate="collapsed">
    @Test
    void isValidStartChar_NullInput() {
        assertThrows(
                NullPointerException.class,
                () -> ValidatorUtil.isValidStartChar(null, 'A'),
                "Null input should throw an exception.");
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
            123456, 1, true
            A234, A, true
            B, B, true
            123456, 3, false
            123456, 5, false
            6, 7, false
            '', 0, false
            """)
    void isValidStartChar(String input, char prefix, boolean expected) {
        boolean actual = ValidatorUtil.isValidStartChar(input, prefix);

        assertEquals(expected, actual, "The validation should return the expected value.");
    }
    // </editor-fold>

    // <editor-fold desc="#isValidChar(String, char, int)" defaultstate="collapsed">
    @Test
    void isValidChar_NullInput() {
        assertThrows(
                NullPointerException.class,
                () -> ValidatorUtil.isValidChar(null, 'A', 0),
                "Null input should throw an exception.");
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
            123456, 1, 0, true
            A234, 3, 2, true
            ABCD, D, 3, true
            12, 2, 1, true
            12, 1, 5, false
            12, 2, 2, false
            123456, 5, 3, false
            6, 6, -7, false
            '', 0, 4, false
            """)
    void isValidChar(String input, char prefix, int position, boolean expected) {
        boolean actual = ValidatorUtil.isValidChar(input, prefix, position);

        assertEquals(expected, actual, "The validation should return the expected value.");
    }
    // </editor-fold>

    // <editor-fold desc="#hasOnlyValidChars(String, ValidChars...)" defaultstate="collapsed">
    @Test
    void hasOnlyValidChars_NullInput() {
        assertThrows(
                NullPointerException.class,
                () -> ValidatorUtil.hasOnlyValidChars(null),
                "Null input should throw an exception.");
    }

    @Test
    void hasOnlyValidChars_NullValidChars() {
        assertThrows(
                NullPointerException.class,
                () -> ValidatorUtil.hasOnlyValidChars("TEST", (ValidatorUtil.ValidChars[]) null),
                "Null input should throw an exception.");
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
            '', true
            123, false
            1234fsd, false
            Ä_-1AÄ, false
            @, false
            [, false
            `, false
            {, false
            """)
    void hasOnlyValidChars(String input, boolean expected) {
        boolean actual = ValidatorUtil.hasOnlyValidChars(
                input,
                ValidatorUtil.ValidChars.A_TO_Z_UPPERCASE,
                ValidatorUtil.ValidChars.A_TO_Z_LOWERCASE);

        assertEquals(expected, actual, "The validation should return the expected value.");
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
            '', true
            sdyfgsdfg, true
            ASDFJKHAS, true
            sdfkjKJHASdasdhkasd, true
            asdOoasdASD, true
            ASDdasd1123, false
            =jksdf%, false
            123, false
            1ASA, false
            _-1A, false
            Ä_-1AÄ, false
            @, false
            [, false
            `, false
            {, false
            """)
    void hasOnlyValidChars_A_TO_Z(String input, boolean expected) {
        boolean actual = ValidatorUtil.hasOnlyValidChars(
                input,
                ValidatorUtil.ValidChars.A_TO_Z_UPPERCASE,
                ValidatorUtil.ValidChars.A_TO_Z_LOWERCASE);

        assertEquals(expected, actual, "The validation should return the expected value.");
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
            '', true
            sdyfgsdfg, true
            ASDFJKHAS, true
            sdfkjKJHASdasdhkasd, true
            o, false
            O, false
            asdOasdASD, false
            asdoasdASD, false
            asdOoasdASD, false
            ASDdasd1123, false
            =jksdf%, false
            123, false
            1ASA, false
            _-1A, false
            Ä_-1AÄ, false
            @, false
            [, false
            `, false
            {, false
            """)
    void hasOnlyValidChars_A_TO_Z_Disallow_O(String input, boolean expected) {
        boolean actual = ValidatorUtil.hasOnlyValidChars(
                input,
                ValidatorUtil.ValidChars.A_TO_Z_UPPERCASE,
                ValidatorUtil.ValidChars.A_TO_Z_LOWERCASE,
                ValidatorUtil.ValidChars.DISALLOW_O);

        assertEquals(expected, actual, "The validation should return the expected value.");
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
            '', true
            123, true
            1, true
            9, true
            0, true
            /, false
            :, false
            1234567890, true
            sdyfgsdfg, false
            ASDFJKHAS, false
            sdfkjKJHASdasdhkasd, false
            asdOasdASD, false
            asdoasdASD, false
            asdOoasdASD, false
            ASDdasd1123, false
            =jksdf%, false
            1ASA, false
            _-1A, false
            Ä_-1AÄ, false
            """)
    void hasOnlyValidChars_Digits(String input, boolean expected) {
        boolean actual = ValidatorUtil.hasOnlyValidChars(input, ValidatorUtil.ValidChars.DIGITS);

        assertEquals(expected, actual, "The validation should return the expected value.");
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
            '', true
            123, true
            1, true
            9, true
            0, false
            /, false
            :, false
            1234567890, false
            sdyfgsdfg, false
            ASDFJKHAS, false
            sdfkjKJHASdasdhkasd, false
            asdOasdASD, false
            asdoasdASD, false
            asdOoasdASD, false
            ASDdasd1123, false
            =jksdf%, false
            1ASA, false
            _-1A, false
            Ä_-1AÄ, false
            """)
    void hasOnlyValidChars_Digits_Disallow_Zero(String input, boolean expected) {
        boolean actual = ValidatorUtil.hasOnlyValidChars(
                input,
                ValidatorUtil.ValidChars.DIGITS,
                ValidatorUtil.ValidChars.DISALLOW_ZERO);

        assertEquals(expected, actual, "The validation should return the expected value.");
    }
    // </editor-fold>

    // <editor-fold desc="ValidChars#contains(ValidChars[], ValidChars)" defaultstate="collapsed">
    @Test
    void contains_NullValidChars() {
        assertThrows(
                NullPointerException.class,
                () -> ValidatorUtil.ValidChars.contains(null, ValidatorUtil.ValidChars.DIGITS),
                "Null input should throw an exception.");
    }

    @Test
    void contains_NullTarget() {
        assertThrows(
                NullPointerException.class,
                () -> ValidatorUtil.ValidChars.contains(
                        new ValidatorUtil.ValidChars[]{ValidatorUtil.ValidChars.DIGITS},
                        null),
                "Null input should throw an exception.");
    }

    @Test
    void contains_EmptyValidChars() {
        ValidatorUtil.ValidChars[] validChars = new ValidatorUtil.ValidChars[0];

        assertFalse(
                ValidatorUtil.ValidChars.contains(validChars, ValidatorUtil.ValidChars.DIGITS),
                "The method should return false.");
    }

    @Test
    void contains_DoesNotContain() {
        ValidatorUtil.ValidChars[] validChars = new ValidatorUtil.ValidChars[]{ValidatorUtil.ValidChars.DIGITS,
                                                                               ValidatorUtil.ValidChars.A_TO_Z_LOWERCASE,
                                                                               ValidatorUtil.ValidChars.DISALLOW_ZERO};

        assertFalse(
                ValidatorUtil.ValidChars.contains(validChars, ValidatorUtil.ValidChars.A_TO_Z_UPPERCASE),
                "The method should return false.");
    }

    @Test
    void contains() {
        ValidatorUtil.ValidChars[] validChars = new ValidatorUtil.ValidChars[]{ValidatorUtil.ValidChars.DIGITS,
                                                                               ValidatorUtil.ValidChars.A_TO_Z_LOWERCASE,
                                                                               ValidatorUtil.ValidChars.DISALLOW_ZERO};

        assertTrue(
                ValidatorUtil.ValidChars.contains(validChars, ValidatorUtil.ValidChars.A_TO_Z_LOWERCASE),
                "The method should return true.");
    }
    // </editor-fold>
}
