package de.derfrzocker.anime.calendar.server.integration.syoboi.service;

import de.derfrzocker.anime.calendar.server.integration.syoboi.api.ChannelId;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.ResolvedChannel;
import de.derfrzocker.anime.calendar.server.model.domain.RequestContext;
import java.util.Optional;

public interface ResolvedChannelService {

    Optional<ResolvedChannel> resolveById(ChannelId id, RequestContext context);
}
