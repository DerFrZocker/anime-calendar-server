package de.derfrzocker.anime.calendar.server.validation;

import static org.junit.jupiter.api.Assertions.*;
import de.derfrzocker.anime.calendar.core.calendar.CalendarId;
import de.derfrzocker.anime.calendar.server.validation.exception.InvalidIdException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class IdValidatorTest {

    // <editor-fold desc="IdValidator#isValid(CalendarId)" defaultstate="collapsed">
    @Test
    public void testIsValid_CalendarId_NullInput() {
        assertFalse(IdValidator.isValid((CalendarId) null), "Null input should not be valid.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"CQAYWSXEDCR", "CMJUNHZBGTVFRCDE"})
    public void testIsValid_CalendarId_ToLong(String id) {
        assertFalse(IdValidator.isValid(new CalendarId(id)), "Id '%s' should not be valid.".formatted(id));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "C", "CY", "CQAYEDCRF",})
    public void testIsValid_CalendarId_ToShort(String id) {
        assertFalse(IdValidator.isValid(new CalendarId(id)), "Id '%s' should not be valid.".formatted(id));
    }

    @ParameterizedTest
    @ValueSource(strings = {"cWSXEDCRFV", "DWSXEDCRFV"})
    public void testIsValid_CalendarId_WrongPrefix(String id) {
        assertFalse(IdValidator.isValid(new CalendarId(id)), "Id '%s' should not be valid.".formatted(id));
    }

    @ParameterizedTest
    @ValueSource(strings = {"COSXEDCRFV", "C0SXEDCRFV", "CFSXE$CRFV", "CFSXEHCRF?", "CFSXEHCRFh", "CFSxE$C^Fh"})
    public void testIsValid_CalendarId_WrongChars(String id) {
        assertFalse(IdValidator.isValid(new CalendarId(id)), "Id '%s' should not be valid.".formatted(id));
    }

    @ParameterizedTest
    @ValueSource(strings = {"CQAYWSXEDC", "CQA1W2XE2C", "C1A1W2HE2C"})
    public void testIsValid_CalendarId_Correct(String id) {
        assertTrue(IdValidator.isValid(new CalendarId(id)), "Id '%s' should be valid.".formatted(id));
    }
    // </editor-fold>

    // <editor-fold desc="IdValidator#validate(CalendarId)" defaultstate="collapsed">
    @Test
    public void testValidate_CalendarId_NullInput() {
        assertThrows(
                InvalidIdException.class,
                () -> IdValidator.validate((CalendarId) null),
                "Null input should not be valid.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"CQAYWSXEDCR", "CMJUNHZBGTVFRCDE"})
    public void testValidate_CalendarId_ToLong(String id) {
        assertThrows(
                InvalidIdException.class,
                () -> IdValidator.validate(new CalendarId(id)),
                "Id '%s' should not be valid.".formatted(id));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "C", "CY", "CQAYEDCRF",})
    public void testValidate_CalendarId_ToShort(String id) {
        assertThrows(
                InvalidIdException.class,
                () -> IdValidator.validate(new CalendarId(id)),
                "Id '%s' should not be valid.".formatted(id));
    }

    @ParameterizedTest
    @ValueSource(strings = {"cWSXEDCRFV", "DWSXEDCRFV"})
    public void testValidate_CalendarId_WrongPrefix(String id) {
        assertThrows(
                InvalidIdException.class,
                () -> IdValidator.validate(new CalendarId(id)),
                "Id '%s' should not be valid.".formatted(id));
    }

    @ParameterizedTest
    @ValueSource(strings = {"COSXEDCRFV", "C0SXEDCRFV", "CFSXE$CRFV", "CFSXEHCRF?", "CFSXEHCRFh", "CFSxE$C^Fh"})
    public void testValidate_CalendarId_WrongChars(String id) {
        assertThrows(
                InvalidIdException.class,
                () -> IdValidator.validate(new CalendarId(id)),
                "Id '%s' should not be valid.".formatted(id));
    }

    @ParameterizedTest
    @ValueSource(strings = {"CQAYWSXEDC", "CQA1W2XE2C", "C1A1W2HE2C"})
    public void testValidate_CalendarId_Correct(String id) {
        assertDoesNotThrow(() -> IdValidator.validate(new CalendarId(id)), "Id '%s' should be valid.".formatted(id));
    }
    // </editor-fold>
}
