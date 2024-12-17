package de.derfrzocker.anime.calendar.server.mongodb.data.name;

import de.derfrzocker.anime.calendar.server.model.domain.name.NameLanguage;
import de.derfrzocker.anime.calendar.server.model.domain.name.NameType;

public record AnimeNameDO(NameType type, NameLanguage language, String name) {

}
