package de.derfrzocker.anime.calendar.server.integration.syoboi.service;

import de.derfrzocker.anime.calendar.server.integration.syoboi.api.ChannelId;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.ProvidedChannel;
import de.derfrzocker.anime.calendar.server.model.domain.RequestContext;
import java.util.Optional;

public interface ChannelProviderService {

    Optional<ProvidedChannel> provideById(ChannelId id, RequestContext context);
}
