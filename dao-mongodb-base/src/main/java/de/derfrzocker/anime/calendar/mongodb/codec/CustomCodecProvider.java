package de.derfrzocker.anime.calendar.mongodb.codec;

import de.derfrzocker.anime.calendar.core.anime.AnimeId;
import de.derfrzocker.anime.calendar.core.animeaccountlink.AnimeAccountLinkId;
import de.derfrzocker.anime.calendar.core.calendar.CalendarId;
import de.derfrzocker.anime.calendar.core.calendar.CalendarKey;
import de.derfrzocker.anime.calendar.core.integration.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.core.integration.IntegrationId;
import de.derfrzocker.anime.calendar.core.notify.NotificationActionId;
import de.derfrzocker.anime.calendar.core.notify.NotificationId;
import de.derfrzocker.anime.calendar.core.user.UserId;
import de.derfrzocker.anime.calendar.server.model.domain.name.NameLanguage;
import de.derfrzocker.anime.calendar.server.model.domain.name.NameType;
import de.derfrzocker.anime.calendar.server.model.domain.user.HashedUserToken;
import java.time.YearMonth;

public class CustomCodecProvider extends AbstractCodecProvider {

    {
        putString(UserId.class, UserId::raw, UserId::new);
        putString(HashedUserToken.class, HashedUserToken::raw, HashedUserToken::new);
        putString(CalendarId.class, CalendarId::raw, CalendarId::new);
        putString(CalendarKey.class, CalendarKey::raw, CalendarKey::new);
        putString(AnimeAccountLinkId.class, AnimeAccountLinkId::raw, AnimeAccountLinkId::new);
        putString(AnimeId.class, AnimeId::raw, AnimeId::new);
        putString(IntegrationId.class, IntegrationId::raw, IntegrationId::new);
        putString(IntegrationAnimeId.class, IntegrationAnimeId::raw, IntegrationAnimeId::new);
        putString(NameType.class, NameType::raw, NameType::new);
        putString(NameLanguage.class, NameLanguage::raw, NameLanguage::new);
        putString(NotificationId.class, NotificationId::raw, NotificationId::new);
        putString(NotificationActionId.class, NotificationActionId::raw, NotificationActionId::new);
        put(YearMonth.class, (value, writer) -> {
            writer.writeStartDocument();
            writer.writeInt32("Year", value.getYear());
            writer.writeInt32("Month", value.getMonthValue());
            writer.writeEndDocument();
        }, reader -> {
            reader.readStartDocument();
            int year = reader.readInt32("Year");
            int month = reader.readInt32("Month");
            reader.readEndDocument();

            return YearMonth.of(year, month);
        });
    }
}
