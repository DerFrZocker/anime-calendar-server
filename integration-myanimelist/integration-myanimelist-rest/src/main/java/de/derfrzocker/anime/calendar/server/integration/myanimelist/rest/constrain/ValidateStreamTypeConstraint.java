package de.derfrzocker.anime.calendar.server.integration.myanimelist.rest.constrain;

import de.derfrzocker.anime.calendar.server.episode.api.StreamType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidateStreamTypeConstraint implements ConstraintValidator<ValidateStreamType, StreamType> {

    @Override
    public boolean isValid(StreamType value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }

        return StreamType.STREAM_TYPES.contains(value);
    }
}
