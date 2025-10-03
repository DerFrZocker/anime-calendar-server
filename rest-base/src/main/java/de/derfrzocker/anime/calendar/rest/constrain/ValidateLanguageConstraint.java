package de.derfrzocker.anime.calendar.rest.constrain;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.List;

public class ValidateLanguageConstraint implements ConstraintValidator<ValidateLanguage, String> {

    private static final List<String> ALLOWED_LANGUAGES = List.of("de", "en");

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }

        return ALLOWED_LANGUAGES.contains(value);
    }
}
