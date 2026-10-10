package de.derfrzocker.anime.calendar.external.mongodb.adapter;

import de.derfrzocker.anime.calendar.external.ExternalAnimeId;
import de.derfrzocker.anime.calendar.external.ExternalAnimeLink;
import de.derfrzocker.anime.calendar.external.mongodb.mapper.ExternalAnimeLinkMapper;
import de.derfrzocker.anime.calendar.external.out.ForManagingExternalAnimeLink;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.function.Function;

@ApplicationScoped
class ExternalAnimeLinkRepository implements ForManagingExternalAnimeLink {

    private static final int BATCH_SIZE = 1000;

    private final ExternalAnimeLinkPanache panache;

    @Inject
    ExternalAnimeLinkRepository(ExternalAnimeLinkPanache panache) {
        this.panache = panache;
    }

    @Override
    public Uni<Void> store(ExternalAnimeLink externalAnimeLink) {
        return panache.persistOrUpdate(ExternalAnimeLinkMapper.toData(externalAnimeLink)).replaceWithVoid();
    }

    @Override
    public Multi<ExternalAnimeLink> retrieveAllWith(Multi<ExternalAnimeId> animeIds) {
        return animeIds
                .group()
                .intoLists()
                .of(BATCH_SIZE)
                .onItem()
                .transformToUni(panache::findByIds)
                .concatenate()
                .onItem()
                .transformToIterable(Function.identity())
                .map(ExternalAnimeLinkMapper::toDomain);
    }
}
