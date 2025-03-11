package de.derfrzocker.anime.calendar.server.integration.syoboi.event;

import de.derfrzocker.anime.calendar.server.integration.syoboi.api.TIDData;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.TIDDataUpdateData;
import de.derfrzocker.anime.calendar.server.model.domain.RequestContext;

public record PostTIDDataUpdateEvent(TIDData current, TIDData updated, TIDDataUpdateData updateData,
                                     RequestContext context) {

}
