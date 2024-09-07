/*
 * MIT License
 *
 * Copyright (c) 2022 Marvin (DerFrZocker)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

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
