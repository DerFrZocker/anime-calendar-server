package de.derfrzocker.anime.calendar.external;

import de.derfrzocker.anime.calendar.util.Change;

import java.util.List;
import java.util.Objects;

public record ExternalAnimeNameUpdateData(Change<List<String>> names) {

    public ExternalAnimeNameUpdateData {
        Objects.requireNonNull(names);
    }
}
