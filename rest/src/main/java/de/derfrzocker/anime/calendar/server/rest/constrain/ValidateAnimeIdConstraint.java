package de.derfrzocker.anime.calendar.server.rest.constrain;

import de.derfrzocker.anime.calendar.core.anime.AnimeId;
import de.derfrzocker.anime.calendar.server.validation.IdValidator;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidateAnimeIdConstraint implements ConstraintValidator<ValidateAnimeId, AnimeId> {

    @Override
    public boolean isValid(AnimeId value, ConstraintValidatorContext context) {
        return IdValidator.isValid(value);
    }
}
