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

package de.derfrzocker.anime.calendar.utils;

import de.derfrzocker.anime.calendar.api.calendar.CalendarId;
import de.derfrzocker.anime.calendar.api.Id;
import de.derfrzocker.anime.calendar.api.IdType;
import de.derfrzocker.anime.calendar.api.anime.AnimeId;
import de.derfrzocker.anime.calendar.api.animeaccountlink.AnimeAccountLinkId;
import de.derfrzocker.anime.calendar.api.calendar.CalendarKey;
import de.derfrzocker.anime.calendar.api.user.UserId;
import java.security.SecureRandom;
import java.util.Random;

public final class StringGenerator {

    // Don't use O and 0 since in many fonts they look to similar
    private static final String CHARS = "ABCDEFGHIJKLMNPQRSTUVWXYZ123456789";

    private StringGenerator() {
    }

    public static AnimeId generateAnimeId() {
        return new AnimeId(generateStringId(IdType.ANIME.prefix(), Id.ID_LENGTH));
    }

    public static UserId generateUserId() {
        return new UserId(generateStringId(IdType.USER.prefix(), Id.ID_LENGTH));
    }

    public static CalendarId generateCalendarId() {
        return new CalendarId(generateStringId(IdType.CALENDAR.prefix(), Id.ID_LENGTH));
    }

    public static AnimeAccountLinkId generateAnimeAccountLink() {
        return new AnimeAccountLinkId(generateStringId(IdType.ANIME_ACCOUNT_LINK.prefix(), Id.ID_LENGTH));
    }

    public static CalendarKey generateCalendarKey(CalendarId calendarId) {
        return new CalendarKey(calendarId.id() + generateStringId(CalendarKey.KEY_PREFIX_CHAR, CalendarKey.CALENDAR_KEY_LENGTH - Id.ID_LENGTH));
    }

    private static String generateStringId(char prefix, int length) {
        Random random = new SecureRandom();
        char[] result = new char[length];

        result[0] = prefix;

        for (int i = 1; i < result.length; i++) {
            result[i] = CHARS.charAt(random.nextInt(CHARS.length()));
        }

        return new String(result);
    }
}