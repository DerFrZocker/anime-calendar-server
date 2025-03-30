package de.derfrzocker.anime.calendar.server.integration.syoboi.service;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.ChannelId;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.ProvidedChannel;
import java.util.Optional;

public interface ChannelProviderService {

    Optional<ProvidedChannel> provideById(ChannelId id, RequestContext context);
}
