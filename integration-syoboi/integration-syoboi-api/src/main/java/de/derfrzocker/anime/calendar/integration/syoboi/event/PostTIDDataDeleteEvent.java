package de.derfrzocker.anime.calendar.integration.syoboi.event;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.integration.syoboi.api.TIDData;

public record PostTIDDataDeleteEvent(TIDData tidData, RequestContext context) {

}
