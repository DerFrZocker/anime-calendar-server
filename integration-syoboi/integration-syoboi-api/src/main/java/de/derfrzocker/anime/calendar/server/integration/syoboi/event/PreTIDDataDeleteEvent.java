package de.derfrzocker.anime.calendar.server.integration.syoboi.event;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.TIDData;

public record PreTIDDataDeleteEvent(TIDData tidData, RequestContext context) {

}
