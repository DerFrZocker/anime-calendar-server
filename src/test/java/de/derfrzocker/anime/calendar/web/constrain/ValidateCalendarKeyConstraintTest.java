package de.derfrzocker.anime.calendar.web.constrain;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import de.derfrzocker.anime.calendar.api.anime.AnimeId;
import de.derfrzocker.anime.calendar.api.calendar.CalendarKey;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class ValidateCalendarKeyConstraintTest {

    @ParameterizedTest
    @ValueSource(strings = {"CNAUSHDX92KUYACKH1HJ25S3D8SRH8"})
    void testCorrectCalendarKey(String value) {
        ValidateCalendarKeyConstraint constraint = new ValidateCalendarKeyConstraint();

        assertTrue(constraint.isValid(new CalendarKey(value), null), format("CalendarKey %s should be valid", value));
    }

    @ParameterizedTest
    @ValueSource(strings = {"ANAUSHDX92KUYACKH1HJ25S3D8SRH8", "cNAUSHDX92KUYACKH1HJ25S3D8SRH8", "CNAUSHDX92VUYACKH1HJ25S3D8SRH8", "CNAUSHDX92kUYACKH1HJ25S3D8SRH8", "CNAUSHDX92KUYACKH1HJ25S3D8srh8", "CNAUSHDX92KUYACKH1HJ2*S3D8SRH8", "CNAUSHDX92KUYACKH1HJ2OS3D8SRH8", "CNAUSHDX92KUYACKH1HJ20S3D8SRH8", "CNAUSHDX92KUYACKH1HJ25S3D8SRH8R", "CNAUSHDX92KUYACKH1HJ25S3D8SR"})
    void testIncorrectAnimeId(String value) {
        ValidateCalendarKeyConstraint constraint = new ValidateCalendarKeyConstraint();

        assertFalse(constraint.isValid(new CalendarKey(value), null), format("CalendarKey %s should be invalid", value));
    }
}
