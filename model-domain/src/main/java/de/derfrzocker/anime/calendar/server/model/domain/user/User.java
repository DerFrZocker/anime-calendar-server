package de.derfrzocker.anime.calendar.server.model.domain.user;

import de.derfrzocker.anime.calendar.server.model.core.animeaccountlink.AnimeAccountLinkId;
import de.derfrzocker.anime.calendar.server.model.core.calendar.CalendarId;
import de.derfrzocker.anime.calendar.server.model.core.user.UserId;
import java.time.Instant;
import java.util.Set;

public record User(UserId userId, Instant createdAt, HashedUserToken hashedUserToken, Set<CalendarId> calendars,
                   Set<AnimeAccountLinkId> animeAccountLinks) {

}
