package de.derfrzocker.anime.calendar.server.integration.syoboi.service;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.ChannelId;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.ResolvedChannel;
import java.util.Optional;

public interface ResolvedChannelService {

    Optional<ResolvedChannel> resolveById(ChannelId id, RequestContext context);
}
