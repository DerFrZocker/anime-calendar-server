package de.derfrzocker.anime.calendar.ical.in;

import de.derfrzocker.anime.calendar.external.ExternalId;
import de.derfrzocker.anime.calendar.external.ExternalUserId;
import io.smallrye.mutiny.Uni;

public interface ForRetrievingICalCalendar {

    Uni<String> retrieve(ExternalId externalType, ExternalUserId externalUserId);
}
