package de.derfrzocker.anime.calendar.integration.name.api;

import de.derfrzocker.anime.calendar.core.util.Change;
import java.util.List;

public record AnimeNameHolderUpdateData(Change<List<AnimeName>> names) {

}
