package de.derfrzocker.anime.calendar.web.constrain;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {ValidateCalendarKeyConstraint.class})
public @interface ValidateCalendarKey {

    String message() default "CalendarKey is not valid";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
