package de.derfrzocker.anime.calendar.server.model.domain.animeaccountlink;

import de.derfrzocker.anime.calendar.server.model.core.animeaccountlink.AnimeAccountLinkId;
import de.derfrzocker.anime.calendar.server.model.core.calendar.CalendarId;
import de.derfrzocker.anime.calendar.server.model.core.user.UserId;
import java.util.Set;

public record AnimeAccountLink(AnimeAccountLinkId animeAccountLinkId, UserId ownerId, AnimeAccountType animeAccountType, Set<CalendarId> linkedCalendar) {
}
