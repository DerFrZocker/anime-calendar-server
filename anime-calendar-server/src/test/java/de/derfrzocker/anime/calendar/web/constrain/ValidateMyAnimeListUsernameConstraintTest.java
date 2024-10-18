package de.derfrzocker.anime.calendar.web.constrain;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import de.derfrzocker.anime.calendar.server.model.core.IntegrationUserId;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class ValidateMyAnimeListUsernameConstraintTest {

    @ParameterizedTest
    @ValueSource(strings = {"AA", "1234567890123456", "arsti23Arsa"})
    void testCorrectAnimeId(String value) {
        ValidateMyAnimeListUsernameConstraint constraint = new ValidateMyAnimeListUsernameConstraint();

        assertTrue(constraint.isValid(new IntegrationUserId(value), null), format("Username %s should be valid", value));
    }

    @ParameterizedTest
    @ValueSource(strings = {"1", "12345678901234567", "@@@" , "arst#$"})
    void testIncorrectAnimeId(String value) {
        ValidateMyAnimeListUsernameConstraint constraint = new ValidateMyAnimeListUsernameConstraint();

        assertFalse(constraint.isValid(new IntegrationUserId(value), null), format("Username %s should be invalid", value));
    }
}
