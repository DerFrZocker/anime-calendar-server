package de.derfrzocker.anime.calendar.web.constrain;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import de.derfrzocker.anime.calendar.server.model.core.AnimeId;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class ValidateIdConstraintTest {

    @ParameterizedTest
    @ValueSource(strings = {"AENRCIERER", "AAAAAAAAAA", "AZZZZZZZZZ", "AENRSTENRS", "AZAZAZA18Z"})
    void testCorrectAnimeId(String value) {
        ValidateIdConstraint constraint = new ValidateIdConstraint();

        assertTrue(constraint.isValid(new AnimeId(value), null), format("Id %s should be valid", value));
    }

    @ParameterizedTest
    @ValueSource(strings = {"UENRCIERER", "aAAAAAAAAA", "XZZZZZZZZZ", "AENRSTENR", "AZAZAZAZaZ", "AZAZAZAZ*Z", "AAAAAAOAAA", "AAAAAA0AAA"})
    void testIncorrectAnimeId(String value) {
        ValidateIdConstraint constraint = new ValidateIdConstraint();

        assertFalse(constraint.isValid(new AnimeId(value), null), format("Id %s should be invalid", value));
    }
}
