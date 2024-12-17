package de.derfrzocker.anime.calendar.server.model.domain.name;

import de.derfrzocker.anime.calendar.server.model.domain.util.Change;
import java.util.List;

public record AnimeNameHolderUpdateData(Change<List<AnimeName>> names) {

}
