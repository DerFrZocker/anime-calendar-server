package de.derfrzocker.anime.calendar.server.validation;

import static org.junit.jupiter.api.Assertions.*;
import de.derfrzocker.anime.calendar.core.anime.AnimeId;
import de.derfrzocker.anime.calendar.core.calendar.CalendarId;
import de.derfrzocker.anime.calendar.core.user.UserId;
import de.derfrzocker.anime.calendar.server.validation.exception.InvalidIdException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class IdValidatorTest {

    // <editor-fold desc="IdValidator#isValid(AnimeId)" defaultstate="collapsed">
    @Test
    public void testIsValid_AnimeId_NullInput() {
        assertFalse(IdValidator.isValid((AnimeId) null), "Null input should not be valid.");
    }

    @Test
    public void testIsValid_AnimeId_NullRawInput() {
        assertFalse(IdValidator.isValid(new AnimeId(null)), "Null Id should not be valid.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"AQAYWSXEDCR", "AMJUNHZBGTVFRCDE"})
    public void testIsValid_AnimeId_ToLong(String id) {
        assertFalse(IdValidator.isValid(new AnimeId(id)), "Id '%s' should not be valid.".formatted(id));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "A", "AY", "AQAYEDCRF",})
    public void testIsValid_AnimeId_ToShort(String id) {
        assertFalse(IdValidator.isValid(new AnimeId(id)), "Id '%s' should not be valid.".formatted(id));
    }

    @ParameterizedTest
    @ValueSource(strings = {"aWSXEDCRFV", "DWSXEDCRFV"})
    public void testIsValid_AnimeId_WrongPrefix(String id) {
        assertFalse(IdValidator.isValid(new AnimeId(id)), "Id '%s' should not be valid.".formatted(id));
    }

    @ParameterizedTest
    @ValueSource(strings = {"AOSXEDCRFV", "A0SXEDCRFV", "AFSXE$CRFV", "AFSXEHCRF?", "AFSXEHCRFh", "AFSxE$C^Fh"})
    public void testIsValid_AnimeId_WrongChars(String id) {
        assertFalse(IdValidator.isValid(new AnimeId(id)), "Id '%s' should not be valid.".formatted(id));
    }

    @ParameterizedTest
    @ValueSource(strings = {"AQAYWSXEDC", "AQA1W2XE2C", "A1A1W2HE2C"})
    public void testIsValid_AnimeId_Correct(String id) {
        assertTrue(IdValidator.isValid(new AnimeId(id)), "Id '%s' should be valid.".formatted(id));
    }
    // </editor-fold>

    // <editor-fold desc="IdValidator#validate(AnimeId)" defaultstate="collapsed">
    @Test
    public void testValidate_AnimeId_NullInput() {
        assertThrows(InvalidIdException.class,
                     () -> IdValidator.validate((AnimeId) null),
                     "Null input should not be valid.");
    }

    @Test
    public void testValidate_AnimeId_NullRawInput() {
        assertThrows(InvalidIdException.class,
                     () -> IdValidator.validate(new AnimeId(null)),
                     "Null Id should not be valid.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"AQAYWSXEDCR", "AMJUNHZBGTVFRCDE"})
    public void testValidate_AnimeId_ToLong(String id) {
        assertThrows(InvalidIdException.class,
                     () -> IdValidator.validate(new AnimeId(id)),
                     "Id '%s' should not be valid.".formatted(id));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "A", "AY", "AQAYEDCRF",})
    public void testValidate_AnimeId_ToShort(String id) {
        assertThrows(InvalidIdException.class,
                     () -> IdValidator.validate(new AnimeId(id)),
                     "Id '%s' should not be valid.".formatted(id));
    }

    @ParameterizedTest
    @ValueSource(strings = {"aWSXEDCRFV", "DWSXEDCRFV"})
    public void testValidate_AnimeId_WrongPrefix(String id) {
        assertThrows(InvalidIdException.class,
                     () -> IdValidator.validate(new AnimeId(id)),
                     "Id '%s' should not be valid.".formatted(id));
    }

    @ParameterizedTest
    @ValueSource(strings = {"AOSXEDCRFV", "A0SXEDCRFV", "AFSXE$CRFV", "AFSXEHCRF?", "AFSXEHCRFh", "AFSxE$C^Fh"})
    public void testValidate_AnimeId_WrongChars(String id) {
        assertThrows(InvalidIdException.class,
                     () -> IdValidator.validate(new AnimeId(id)),
                     "Id '%s' should not be valid.".formatted(id));
    }

    @ParameterizedTest
    @ValueSource(strings = {"AQAYWSXEDC", "AQA1W2XE2C", "A1A1W2HE2C"})
    public void testValidate_AnimeId_Correct(String id) {
        assertDoesNotThrow(() -> IdValidator.validate(new AnimeId(id)), "Id '%s' should be valid.".formatted(id));
    }
    // </editor-fold>

    // <editor-fold desc="IdValidator#isValid(UserId)" defaultstate="collapsed">
    @Test
    public void testIsValid_UserId_NullInput() {
        assertFalse(IdValidator.isValid((UserId) null), "Null input should not be valid.");
    }

    @Test
    public void testIsValid_UserId_NullRawInput() {
        assertFalse(IdValidator.isValid(new UserId(null)), "Null Id should not be valid.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"UQAYWSXEDCR", "UMJUNHZBGTVFRCDE"})
    public void testIsValid_UserId_ToLong(String id) {
        assertFalse(IdValidator.isValid(new UserId(id)), "Id '%s' should not be valid.".formatted(id));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "U", "UY", "UQAYEDCRF",})
    public void testIsValid_UserId_ToShort(String id) {
        assertFalse(IdValidator.isValid(new UserId(id)), "Id '%s' should not be valid.".formatted(id));
    }

    @ParameterizedTest
    @ValueSource(strings = {"uWSXEDCRFV", "DWSXEDCRFV"})
    public void testIsValid_UserId_WrongPrefix(String id) {
        assertFalse(IdValidator.isValid(new UserId(id)), "Id '%s' should not be valid.".formatted(id));
    }

    @ParameterizedTest
    @ValueSource(strings = {"UOSXEDCRFV", "U0SXEDCRFV", "UFSXE$CRFV", "UFSXEHCRF?", "UFSXEHCRFh", "UFSxE$C^Fh"})
    public void testIsValid_UserId_WrongChars(String id) {
        assertFalse(IdValidator.isValid(new UserId(id)), "Id '%s' should not be valid.".formatted(id));
    }

    @ParameterizedTest
    @ValueSource(strings = {"UQAYWSXEDC", "UQA1W2XE2C", "U1A1W2HE2C"})
    public void testIsValid_UserId_Correct(String id) {
        assertTrue(IdValidator.isValid(new UserId(id)), "Id '%s' should be valid.".formatted(id));
    }
    // </editor-fold>

    // <editor-fold desc="IdValidator#validate(UserId)" defaultstate="collapsed">
    @Test
    public void testValidate_UserId_NullInput() {
        assertThrows(InvalidIdException.class,
                     () -> IdValidator.validate((UserId) null),
                     "Null input should not be valid.");
    }

    @Test
    public void testValidate_UserId_NullRawInput() {
        assertThrows(InvalidIdException.class,
                     () -> IdValidator.validate(new UserId(null)),
                     "Null Id should not be valid.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"UQAYWSXEDCR", "UMJUNHZBGTVFRCDE"})
    public void testValidate_UserId_ToLong(String id) {
        assertThrows(InvalidIdException.class,
                     () -> IdValidator.validate(new UserId(id)),
                     "Id '%s' should not be valid.".formatted(id));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "U", "UY", "UQAYEDCRF",})
    public void testValidate_UserId_ToShort(String id) {
        assertThrows(InvalidIdException.class,
                     () -> IdValidator.validate(new UserId(id)),
                     "Id '%s' should not be valid.".formatted(id));
    }

    @ParameterizedTest
    @ValueSource(strings = {"uWSXEDCRFV", "DWSXEDCRFV"})
    public void testValidate_UserId_WrongPrefix(String id) {
        assertThrows(InvalidIdException.class,
                     () -> IdValidator.validate(new UserId(id)),
                     "Id '%s' should not be valid.".formatted(id));
    }

    @ParameterizedTest
    @ValueSource(strings = {"UOSXEDCRFV", "U0SXEDCRFV", "UFSXE$CRFV", "UFSXEHCRF?", "UFSXEHCRFh", "UFSxE$C^Fh"})
    public void testValidate_UserId_WrongChars(String id) {
        assertThrows(InvalidIdException.class,
                     () -> IdValidator.validate(new UserId(id)),
                     "Id '%s' should not be valid.".formatted(id));
    }

    @ParameterizedTest
    @ValueSource(strings = {"UQAYWSXEDC", "UQA1W2XE2C", "U1A1W2HE2C"})
    public void testValidate_UserId_Correct(String id) {
        assertDoesNotThrow(() -> IdValidator.validate(new UserId(id)), "Id '%s' should be valid.".formatted(id));
    }
    // </editor-fold>

    // <editor-fold desc="IdValidator#isValid(CalendarId)" defaultstate="collapsed">
    @Test
    public void testIsValid_CalendarId_NullInput() {
        assertFalse(IdValidator.isValid((CalendarId) null), "Null input should not be valid.");
    }

    @Test
    public void testIsValid_CalendarId_NullRawInput() {
        assertFalse(IdValidator.isValid(new CalendarId(null)), "Null Id should not be valid.");
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
        assertThrows(InvalidIdException.class,
                     () -> IdValidator.validate((CalendarId) null),
                     "Null input should not be valid.");
    }

    @Test
    public void testValidate_CalendarId_NullRawInput() {
        assertThrows(InvalidIdException.class,
                     () -> IdValidator.validate(new CalendarId(null)),
                     "Null Id should not be valid.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"CQAYWSXEDCR", "CMJUNHZBGTVFRCDE"})
    public void testValidate_CalendarId_ToLong(String id) {
        assertThrows(InvalidIdException.class,
                     () -> IdValidator.validate(new CalendarId(id)),
                     "Id '%s' should not be valid.".formatted(id));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "C", "CY", "CQAYEDCRF",})
    public void testValidate_CalendarId_ToShort(String id) {
        assertThrows(InvalidIdException.class,
                     () -> IdValidator.validate(new CalendarId(id)),
                     "Id '%s' should not be valid.".formatted(id));
    }

    @ParameterizedTest
    @ValueSource(strings = {"cWSXEDCRFV", "DWSXEDCRFV"})
    public void testValidate_CalendarId_WrongPrefix(String id) {
        assertThrows(InvalidIdException.class,
                     () -> IdValidator.validate(new CalendarId(id)),
                     "Id '%s' should not be valid.".formatted(id));
    }

    @ParameterizedTest
    @ValueSource(strings = {"COSXEDCRFV", "C0SXEDCRFV", "CFSXE$CRFV", "CFSXEHCRF?", "CFSXEHCRFh", "CFSxE$C^Fh"})
    public void testValidate_CalendarId_WrongChars(String id) {
        assertThrows(InvalidIdException.class,
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
