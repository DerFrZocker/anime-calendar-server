package de.derfrzocker.anime.calendar.server.model.domain.animeaccountlink;

import de.derfrzocker.anime.calendar.server.model.core.AnimeAccountLinkId;
import de.derfrzocker.anime.calendar.server.model.core.CalendarId;
import de.derfrzocker.anime.calendar.server.model.core.UserId;
import java.util.Set;

public record AnimeAccountLink(AnimeAccountLinkId animeAccountLinkId, UserId ownerId, AnimeAccountType animeAccountType, Set<CalendarId> linkedCalendar) {
}
