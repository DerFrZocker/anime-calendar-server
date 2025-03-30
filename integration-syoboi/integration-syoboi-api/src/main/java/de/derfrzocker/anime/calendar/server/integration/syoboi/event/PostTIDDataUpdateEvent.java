package de.derfrzocker.anime.calendar.server.integration.syoboi.event;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.TIDData;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.TIDDataUpdateData;

public record PostTIDDataUpdateEvent(TIDData current, TIDData updated, TIDDataUpdateData updateData,
                                     RequestContext context) {

}
