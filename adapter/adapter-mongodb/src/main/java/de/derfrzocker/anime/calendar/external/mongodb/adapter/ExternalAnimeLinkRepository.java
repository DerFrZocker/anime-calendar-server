package de.derfrzocker.anime.calendar.external.mongodb.adapter;

import de.derfrzocker.anime.calendar.external.ExternalAnimeLink;
import de.derfrzocker.anime.calendar.external.mongodb.mapper.ExternalAnimeLinkMapper;
import de.derfrzocker.anime.calendar.external.out.ForManagingExternalAnimeLink;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
class ExternalAnimeLinkRepository implements ForManagingExternalAnimeLink {

    private final ExternalAnimeLinkPanache panache;

    @Inject
    ExternalAnimeLinkRepository(ExternalAnimeLinkPanache panache) {
        this.panache = panache;
    }

    @Override
    public Uni<Void> store(ExternalAnimeLink externalAnimeLink) {
        return panache.persistOrUpdate(ExternalAnimeLinkMapper.toData(externalAnimeLink)).replaceWithVoid();
    }
}
