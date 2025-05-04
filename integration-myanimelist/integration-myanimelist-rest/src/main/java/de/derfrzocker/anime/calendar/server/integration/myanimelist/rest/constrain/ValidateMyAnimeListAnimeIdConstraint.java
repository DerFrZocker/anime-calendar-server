package de.derfrzocker.anime.calendar.server.integration.myanimelist.rest.constrain;

import de.derfrzocker.anime.calendar.core.integration.IntegrationAnimeId;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidateMyAnimeListAnimeIdConstraint implements ConstraintValidator<ValidateMyAnimeListAnimeId, IntegrationAnimeId> {

    // Some semi-random length limit, which will probably cause problems in the future
    private static final int MAX_ANIME_ID_LENGTH = 7;

    @Override
    public boolean isValid(IntegrationAnimeId value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }

        if (value.raw().length() > MAX_ANIME_ID_LENGTH) {
            return false;
        }

        for (int i = 0; i < value.raw().length(); i++) {
            char c = value.raw().charAt(i);
            if (c < '0' || c > '9') {
                return false;
            }
        }

        return true;
    }
}
