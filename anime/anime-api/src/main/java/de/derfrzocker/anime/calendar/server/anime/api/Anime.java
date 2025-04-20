package de.derfrzocker.anime.calendar.server.anime.api;

import de.derfrzocker.anime.calendar.core.ModificationInfo;
import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.anime.AnimeId;
import de.derfrzocker.anime.calendar.core.user.UserId;
import de.derfrzocker.anime.calendar.server.anime.api.layer.LayerHolder;
import de.derfrzocker.anime.calendar.server.layer2.api.LayerStepConfig;
import java.time.Instant;
import java.util.List;

public record Anime(AnimeId id, Instant createdAt, UserId createdBy, Instant updatedAt, UserId updatedBy, String title,
                    int episodeCount, List<LayerStepConfig> episodeLayers,
                    List<LayerHolder> oldEpisodeLayers) implements ModificationInfo {

    public static Anime from(AnimeId id, AnimeCreateData createData, RequestContext context) {
        return new Anime(id,
                         context.requestTime(),
                         context.requestUser(),
                         context.requestTime(),
                         context.requestUser(),
                         createData.title(),
                         createData.episodeCount(),
                         createData.episodeLayers(),
                         List.of());
    }

    public Anime updateWithData(AnimeUpdateData updateData, RequestContext context) {
        return new Anime(id(),
                         createdAt(),
                         createdBy(),
                         context.requestTime(),
                         context.requestUser(),
                         updateData.title().apply(title()),
                         updateData.episodeCount().apply(episodeCount()),
                         updateData.episodeLayers().apply(episodeLayers()),
                         oldEpisodeLayers());
    }
}
