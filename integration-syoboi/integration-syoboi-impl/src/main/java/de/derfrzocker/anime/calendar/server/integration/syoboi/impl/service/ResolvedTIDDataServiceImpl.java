package de.derfrzocker.anime.calendar.server.integration.syoboi.impl.service;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.util.Change;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.ProvidedTIDData;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.ResolvedTIDData;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.TID;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.TIDData;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.TIDDataCreateData;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.TIDDataUpdateData;
import de.derfrzocker.anime.calendar.server.integration.syoboi.impl.config.ResolvedTIDDataServiceConfig;
import de.derfrzocker.anime.calendar.server.integration.syoboi.service.ResolvedTIDDataService;
import de.derfrzocker.anime.calendar.server.integration.syoboi.service.TIDDataProviderService;
import de.derfrzocker.anime.calendar.server.integration.syoboi.service.TIDDataService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.time.Instant;
import java.util.Optional;

@ApplicationScoped
public class ResolvedTIDDataServiceImpl implements ResolvedTIDDataService {

    @Inject
    TIDDataService service;
    @Inject
    TIDDataProviderService provider;
    @Inject
    ResolvedTIDDataServiceConfig config;

    @Override
    public Optional<ResolvedTIDData> resolveById(TID id, RequestContext context) {
        return this.service
                .getById(id, context)
                .flatMap(tidData -> updateIfNeeded(tidData, context))
                .or(() -> createNew(id, context))
                .map(ResolvedTIDData::from);
    }

    private Optional<TIDData> updateIfNeeded(TIDData tidData, RequestContext context) {
        if (isValid(tidData)) {
            return Optional.of(tidData);
        }

        Optional<ProvidedTIDData> providedData = this.provider.provideById(tidData.tid(), context);

        if (providedData.isEmpty()) {
            this.service.deleteById(tidData.tid(), context);
            return Optional.empty();
        }

        return Optional.of(this.service.updateWithData(tidData.tid(), toUpdateData(providedData.get()), context));
    }

    private Optional<TIDData> createNew(TID id, RequestContext context) {
        return this.provider.provideById(id, context).map(providedTIDData -> this.service.createWithData(
                id,
                toCreateData(
                        providedTIDData),
                context));

    }

    private TIDDataUpdateData toUpdateData(ProvidedTIDData providedTIDData) {
        return new TIDDataUpdateData(
                Change.to(providedTIDData.title()),
                Change.nothing(),
                Change.to(providedTIDData.firstStart()),
                Change.to(providedTIDData.firstEnd()),
                Change.to(providedTIDData.firstChannelIds()),
                Change.nothing(),
                Change.to(createValidUntil(providedTIDData)));
    }

    private TIDDataCreateData toCreateData(ProvidedTIDData providedTIDData) {
        return new TIDDataCreateData(
                providedTIDData.title(),
                null,
                providedTIDData.firstStart(),
                providedTIDData.firstEnd(),
                providedTIDData.firstChannelIds(),
                true,
                createValidUntil(providedTIDData));
    }

    private Instant createValidUntil(ProvidedTIDData providedTIDData) {
        if (providedTIDData.firstEnd() != null) {
            return null;
        }

        return Instant.now().plus(this.config.validDuration());
    }

    private boolean isValid(TIDData tidData) {
        if (tidData.validUntil() == null) {
            return true;
        }

        return Instant.now().isBefore(tidData.validUntil());
    }
}
