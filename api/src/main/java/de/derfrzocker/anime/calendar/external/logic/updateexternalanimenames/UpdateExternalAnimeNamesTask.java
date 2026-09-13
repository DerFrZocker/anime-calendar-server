package de.derfrzocker.anime.calendar.external.logic.updateexternalanimenames;

import de.derfrzocker.anime.calendar.RequestContext;
import de.derfrzocker.anime.calendar.external.ExternalAnimeNameHolder;
import de.derfrzocker.anime.calendar.external.ExternalAnimeNameCreateData;
import de.derfrzocker.anime.calendar.external.ExternalAnimeNameId;
import de.derfrzocker.anime.calendar.external.ExternalAnimeNameUpdateData;
import de.derfrzocker.anime.calendar.external.out.FetchedExternalAnimeNameHolder;
import de.derfrzocker.anime.calendar.external.in.ForExternalUpdatingAnimeNames;
import de.derfrzocker.anime.calendar.external.out.ForFetchingExternalAnimeNames;
import de.derfrzocker.anime.calendar.external.out.ForManagingExternalAnimeNames;
import de.derfrzocker.anime.calendar.util.Change;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.tuples.Tuple2;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static de.derfrzocker.anime.calendar.logic.MutinyUtils.optional;
import static de.derfrzocker.anime.calendar.logic.MutinyUtils.with;

@ApplicationScoped
class UpdateExternalAnimeNamesTask implements ForExternalUpdatingAnimeNames {

    private final Instance<ForFetchingExternalAnimeNames> fetchers;
    private final ForManagingExternalAnimeNames animeNamesRepository;

    @Inject
    UpdateExternalAnimeNamesTask(
            Instance<ForFetchingExternalAnimeNames> fetchers,
            ForManagingExternalAnimeNames animeNamesRepository) {
        this.fetchers = fetchers;
        this.animeNamesRepository = animeNamesRepository;
    }

    @Override
    public Uni<Void> updateAnimeNames(RequestContext context) {
        return Multi.createBy()
                .concatenating()
                .streams(fromAllFetchers(context))
                .onItem()
                .transformToUniAndConcatenate(fetched -> getCurrent(fetched, context).stage(with(fetched)))
//                .onItem()
//                .call(ifPresent(Tuple2::getItem1, (current, tuple) -> update(current, tuple.getItem2(), context)))
//                .onItem()
//                .call(ifEmpty(Tuple2::getItem1, tuple -> insert(tuple.getItem2(), context)))
                .onItem()
                .transformToUni(optional(
                        Tuple2::getItem1,
                        (current, tuple) -> update(current, tuple.getItem2(), context),
                        tuple -> insert(tuple.getItem2(), context)))
                .concatenate()
                .onItem()
                .ignoreAsUni();
    }


    private List<Multi<FetchedExternalAnimeNameHolder>> fromAllFetchers(RequestContext context) {
        return fetchers.stream().map(fetcher -> fetcher.fetchAnimeNames(context)).toList();
    }

    private Uni<Optional<ExternalAnimeNameHolder>> getCurrent(FetchedExternalAnimeNameHolder animeName, RequestContext context) {
        return animeNamesRepository.getBy(
                animeName.externalId(),
                animeName.externalAnimeId(),
                animeName.type(),
                animeName.language(),
                context);
    }

    private Uni<Void> insert(FetchedExternalAnimeNameHolder fetched, RequestContext context) {
        return Uni.createFrom()
                .item(fetched)
                .map(this::toCreateData)
                .map(createData -> ExternalAnimeNameHolder.from(newId(), createData, context))
                .flatMap(animeName -> animeNamesRepository.insert(animeName, context));
    }

    private Uni<Void> update(ExternalAnimeNameHolder current, FetchedExternalAnimeNameHolder fetched, RequestContext context) {
        return Uni.createFrom()
                .item(fetched)
                .map(this::toUpdateData)
                .map(updateData -> current.update(updateData, context))
                .flatMap(animeName -> animeNamesRepository.update(animeName, context));
    }

    private Uni<Void> updateAnimes(ExternalAnimeNameHolder name, RequestContext context) {
        // TODO 2026-09-13: Implement me
        return null;
    }

    private ExternalAnimeNameCreateData toCreateData(FetchedExternalAnimeNameHolder fetched) {
        return new ExternalAnimeNameCreateData(
                fetched.externalId(),
                fetched.externalAnimeId(),
                fetched.type(),
                fetched.language(),
                fetched.names());
    }

    private ExternalAnimeNameId newId() {
        return ExternalAnimeNameId.of(UUID.randomUUID());
    }

    private ExternalAnimeNameUpdateData toUpdateData(FetchedExternalAnimeNameHolder fetched) {
        return new ExternalAnimeNameUpdateData(Change.to(fetched.names()));
    }
}
