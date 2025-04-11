package de.derfrzocker.anime.calendar.server.validation;

import static org.junit.jupiter.api.Assertions.*;
import de.derfrzocker.anime.calendar.core.calendar.CalendarKey;
import de.derfrzocker.anime.calendar.server.validation.exception.InvalidKeyException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class KeyValidatorTest {

    // <editor-fold desc="KeyValidator#isValid(CalendarKey)" defaultstate="collapsed">
    @Test
    public void testIsValid_CalendarKey_NullInput() {
        assertFalse(KeyValidator.isValid((CalendarKey) null), "Null input should not be valid.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"CQAYWSXEDCKAWSXEDCRFVTGBZHNQAYWDCRFVTGBQD",
                            "CQAYWSXEDCKAWSXEDCRFVTGBZHNQAYWDCRFVTGBQDASDASD"})
    public void testIsValid_CalendarKey_ToLong(String key) {
        assertFalse(KeyValidator.isValid(new CalendarKey(key)), "Key '%s' should not be valid.".formatted(key));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "C", "CY", "CQAYWSXEDCKAWSXEDCRFVTGBZHNQAYWDCRFVTGB",})
    public void testIsValid_CalendarKey_ToShort(String key) {
        assertFalse(KeyValidator.isValid(new CalendarKey(key)), "Key '%s' should not be valid.".formatted(key));
    }

    @ParameterizedTest
    @ValueSource(strings = {"cQAYWSXEDCKAWSXEDCRFVTGBZHNQAYWDCRFVTGBA", "DQAYWSXEDCKAWSXEDCRFVTGBZHNQAYWDCRFVTGBA"})
    public void testIsValid_CalendarKey_WrongIdPrefix(String key) {
        assertFalse(KeyValidator.isValid(new CalendarKey(key)), "Key '%s' should not be valid.".formatted(key));
    }

    @ParameterizedTest
    @ValueSource(strings = {"CQAYWSXEDCkAWSXEDCRFVTGBZHNQAYWDCRFVTGBA", "CQAYWSXEDCDAWSXEDCRFVTGBZHNQAYWDCRFVTGBA"})
    public void testIsValid_CalendarKey_WrongKeyPrefix(String key) {
        assertFalse(KeyValidator.isValid(new CalendarKey(key)), "Key '%s' should not be valid.".formatted(key));
    }

    @ParameterizedTest
    @ValueSource(strings = {"COAYWSXEDCKAWSXEDCRFVTGBZHNQAYWDCRFVTGBA",
                            "C0AYWSXEDCKAWSXEDCRFVTGBZHNQAYWDCRFVTGBA",
                            "CAAYWSXEDCKAWSXEDCRFVTG$ZHNQAYWDCRFVTGBA",
                            "CAAYWSXEDCKAWSXEDCRFVTGBZH^QAYWDCRFVTGBA",
                            "CAAYWSXEDcKAWSXEDCrFVTGBZHNQA^WD^RFVTGBA",
                            "CaAYWSXEDCKAWSXEACRFATGBZ0NQAYWDCRFVTGBA"})
    public void testIsValid_CalendarKey_WrongChars(String key) {
        assertFalse(KeyValidator.isValid(new CalendarKey(key)), "Key '%s' should not be valid.".formatted(key));
    }

    @ParameterizedTest
    @ValueSource(strings = {"C1AYWSXEDCKAWSXEDCRFVTGBZHNQAYWDCRFVTGBA",
                            "C4AYWSXEDCKAWSXEDCRFVTGBZHNQAYWDCRFVTGBA",
                            "CAAYWSXEDCKAWSXEDCRFVTG1ZHNQAYWDCRFVTGBA",
                            "CAAYWSXEDCKA4SXEDCRFVTGBZH1QAYWDCRFVTGBA",
                            "CAAYWSXEDCK3WSXEDCRFVTGBZHNQA9WD7RFVTGBA",
                            "CAAYWSXEDCKAWSXE2CRF9TGBZ8NQAYWDCRFVTGBA"})
    public void testIsValid_CalendarKey_Correct(String key) {
        assertTrue(KeyValidator.isValid(new CalendarKey(key)), "Key '%s' should be valid.".formatted(key));
    }
    // </editor-fold>

    // <editor-fold desc="KeyValidator#validate(CalendarKey)" defaultstate="collapsed">
    @Test
    public void testValidate_CalendarKey_NullInput() {
        assertThrows(InvalidKeyException.class,
                     () -> KeyValidator.validate((CalendarKey) null),
                     "Null input should not be valid.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"CQAYWSXEDCKAWSXEDCRFVTGBZHNQAYWDCRFVTGBQD",
                            "CQAYWSXEDCKAWSXEDCRFVTGBZHNQAYWDCRFVTGBQDASDASD"})
    public void testValidate_CalendarKey_ToLong(String key) {
        assertThrows(InvalidKeyException.class,
                     () -> KeyValidator.validate(new CalendarKey(key)),
                     "Key '%s' should not be valid.".formatted(key));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "C", "CY", "CQAYWSXEDCKAWSXEDCRFVTGBZHNQAYWDCRFVTGB",})
    public void testValidate_CalendarKey_ToShort(String key) {
        assertThrows(InvalidKeyException.class,
                     () -> KeyValidator.validate(new CalendarKey(key)),
                     "Key '%s' should not be valid.".formatted(key));
    }

    @ParameterizedTest
    @ValueSource(strings = {"cQAYWSXEDCKAWSXEDCRFVTGBZHNQAYWDCRFVTGBA", "DQAYWSXEDCKAWSXEDCRFVTGBZHNQAYWDCRFVTGBA"})
    public void testValidate_CalendarKey_WrongIdPrefix(String key) {
        assertThrows(InvalidKeyException.class,
                     () -> KeyValidator.validate(new CalendarKey(key)),
                     "Key '%s' should not be valid.".formatted(key));
    }

    @ParameterizedTest
    @ValueSource(strings = {"CQAYWSXEDCkAWSXEDCRFVTGBZHNQAYWDCRFVTGBA", "CQAYWSXEDCDAWSXEDCRFVTGBZHNQAYWDCRFVTGBA"})
    public void testValidate_CalendarKey_WrongKeyPrefix(String key) {
        assertThrows(InvalidKeyException.class,
                     () -> KeyValidator.validate(new CalendarKey(key)),
                     "Key '%s' should not be valid.".formatted(key));
    }

    @ParameterizedTest
    @ValueSource(strings = {"COAYWSXEDCKAWSXEDCRFVTGBZHNQAYWDCRFVTGBA",
                            "C0AYWSXEDCKAWSXEDCRFVTGBZHNQAYWDCRFVTGBA",
                            "CAAYWSXEDCKAWSXEDCRFVTG$ZHNQAYWDCRFVTGBA",
                            "CAAYWSXEDCKAWSXEDCRFVTGBZH^QAYWDCRFVTGBA",
                            "CAAYWSXEDcKAWSXEDCrFVTGBZHNQA^WD^RFVTGBA",
                            "CaAYWSXEDCKAWSXEACRFATGBZ0NQAYWDCRFVTGBA"})
    public void testValidate_CalendarKey_WrongChars(String key) {
        assertThrows(InvalidKeyException.class,
                     () -> KeyValidator.validate(new CalendarKey(key)),
                     "Key '%s' should not be valid.".formatted(key));
    }

    @ParameterizedTest
    @ValueSource(strings = {"C1AYWSXEDCKAWSXEDCRFVTGBZHNQAYWDCRFVTGBA",
                            "C4AYWSXEDCKAWSXEDCRFVTGBZHNQAYWDCRFVTGBA",
                            "CAAYWSXEDCKAWSXEDCRFVTG1ZHNQAYWDCRFVTGBA",
                            "CAAYWSXEDCKA4SXEDCRFVTGBZH1QAYWDCRFVTGBA",
                            "CAAYWSXEDCK3WSXEDCRFVTGBZHNQA9WD7RFVTGBA",
                            "CAAYWSXEDCKAWSXE2CRF9TGBZ8NQAYWDCRFVTGBA"})
    public void testValidate_CalendarKey_Correct(String key) {
        assertDoesNotThrow(() -> KeyValidator.validate(new CalendarKey(key)),
                           "Key '%s' should be valid.".formatted(key));
    }
    // </editor-fold>
}
