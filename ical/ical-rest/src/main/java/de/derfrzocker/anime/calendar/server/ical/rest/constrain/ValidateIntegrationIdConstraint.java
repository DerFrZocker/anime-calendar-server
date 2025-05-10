package de.derfrzocker.anime.calendar.server.ical.rest.constrain;

import de.derfrzocker.anime.calendar.core.integration.IntegrationId;
import de.derfrzocker.anime.calendar.core.integration.IntegrationIds;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Set;

public class ValidateIntegrationIdConstraint implements ConstraintValidator<ValidateIntegrationId, IntegrationId> {

    private static final Set<IntegrationId> ALLOWED = Set.of(IntegrationIds.ANIDB, IntegrationIds.MY_ANIME_LIST,
                                                             IntegrationIds.SYOBOI);

    @Override
    public boolean isValid(IntegrationId value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        return ALLOWED.contains(value);
    }
}
