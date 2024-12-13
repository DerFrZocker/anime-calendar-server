package de.derfrzocker.anime.calendar.server.model.domain.anime;

import de.derfrzocker.anime.calendar.server.model.core.anime.AnimeId;
import de.derfrzocker.anime.calendar.server.model.core.user.UserId;
import de.derfrzocker.anime.calendar.server.model.domain.ModificationInfo;
import de.derfrzocker.anime.calendar.server.model.domain.RequestContext;
import de.derfrzocker.anime.calendar.server.model.domain.layer.LayerHolder;
import java.time.Instant;
import java.util.List;

public record Anime(AnimeId id, Instant createdAt, UserId createdBy, Instant updatedAt, UserId updatedBy, String title,
                    int episodeCount, List<LayerHolder> episodeLayers) implements ModificationInfo {

    public static Anime from(AnimeId id, AnimeCreateData createData, RequestContext context) {
        return new Anime(id,
                         context.requestTime(),
                         context.requestUser(),
                         context.requestTime(),
                         context.requestUser(),
                         createData.title(),
                         createData.episodeCount(),
                         createData.episodeLayers());
    }

    public Anime updateWithData(AnimeUpdateData updateData, RequestContext context) {
        return new Anime(id(),
                         createdAt(),
                         createdBy(),
                         context.requestTime(),
                         context.requestUser(),
                         updateData.title().apply(title()),
                         updateData.episodeCount().apply(episodeCount()),
                         updateData.episodeLayers().apply(episodeLayers()));
    }
}
