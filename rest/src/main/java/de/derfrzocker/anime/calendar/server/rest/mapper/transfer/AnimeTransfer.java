package de.derfrzocker.anime.calendar.server.rest.mapper.transfer;

import de.derfrzocker.anime.calendar.server.model.domain.anime.AnimeCreateData;
import de.derfrzocker.anime.calendar.server.model.domain.anime.AnimeUpdateData;
import de.derfrzocker.anime.calendar.server.model.domain.util.FixedChange;
import de.derfrzocker.anime.calendar.server.model.domain.util.NoChange;
import de.derfrzocker.anime.calendar.server.rest.transfer.anime.AnimeCreateDataTO;
import de.derfrzocker.anime.calendar.server.rest.transfer.anime.AnimeUpdateDataTO;
import java.util.List;

public final class AnimeTransfer {

    public static AnimeCreateData toDomain(AnimeCreateDataTO transfer) {
        // TODO 2024-12-15: Support layers
        return new AnimeCreateData(transfer.title(), transfer.episodeCount(), List.of());
    }

    public static AnimeUpdateData toDomain(AnimeUpdateDataTO transfer) {
        // TODO 2024-12-15: Support layers
        return new AnimeUpdateData(FixedChange.of(transfer.title()),
                                   FixedChange.of(transfer.episodeCount()),
                                   NoChange.noChange());
    }

    private AnimeTransfer() {

    }
}