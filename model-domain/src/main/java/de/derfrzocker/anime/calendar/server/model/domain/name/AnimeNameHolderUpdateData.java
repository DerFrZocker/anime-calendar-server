package de.derfrzocker.anime.calendar.server.model.domain.name;

import de.derfrzocker.anime.calendar.core.util.Change;
import java.util.List;

public record AnimeNameHolderUpdateData(Change<List<AnimeName>> names) {

}
