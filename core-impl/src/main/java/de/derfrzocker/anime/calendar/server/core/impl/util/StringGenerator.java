package de.derfrzocker.anime.calendar.server.core.impl.util;

import de.derfrzocker.anime.calendar.server.model.core.anime.AnimeId;
import de.derfrzocker.anime.calendar.server.model.core.animeaccountlink.AnimeAccountLinkId;
import de.derfrzocker.anime.calendar.server.model.core.calendar.CalendarId;
import de.derfrzocker.anime.calendar.server.model.core.calendar.CalendarKey;
import de.derfrzocker.anime.calendar.server.model.core.user.UserId;
import de.derfrzocker.anime.calendar.server.model.domain.user.UserToken;
import java.security.SecureRandom;
import java.util.Random;

public final class StringGenerator {

    // Don't use O and 0 since in many fonts they look to similar
    private static final String CHARS = "ABCDEFGHIJKLMNPQRSTUVWXYZ123456789";

    private StringGenerator() {
    }

    public static AnimeId generateAnimeId() {
        return new AnimeId(generateStringId(AnimeId.ID_PREFIX, AnimeId.ID_LENGTH));
    }

    public static UserId generateUserId() {
        return new UserId(generateStringId(UserId.ID_PREFIX, UserId.ID_LENGTH));
    }

    public static CalendarId generateCalendarId() {
        return new CalendarId(generateStringId(CalendarId.ID_PREFIX, CalendarId.ID_LENGTH));
    }

    public static AnimeAccountLinkId generateAnimeAccountLink() {
        return new AnimeAccountLinkId(generateStringId(AnimeAccountLinkId.ID_PREFIX, AnimeAccountLinkId.ID_LENGTH));
    }

    public static CalendarKey generateCalendarKey(CalendarId calendarId) {
        return new CalendarKey(calendarId.raw() + generateStringId(CalendarKey.KEY_PREFIX_CHAR,
                                                                   CalendarKey.KEY_LENGTH - CalendarId.ID_LENGTH));
    }

    public static UserToken generateUserToken(UserId id) {
        return new UserToken(id.raw() + generateStringId(UserToken.TOKEN_PREFIX_CHAR,
                                                         UserToken.TOKEN_KEY_LENGTH - UserId.ID_LENGTH));
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
