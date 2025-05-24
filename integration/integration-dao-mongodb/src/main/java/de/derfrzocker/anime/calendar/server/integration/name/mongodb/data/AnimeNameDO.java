package de.derfrzocker.anime.calendar.server.integration.name.mongodb.data;

import de.derfrzocker.anime.calendar.server.integration.name.api.NameLanguage;
import de.derfrzocker.anime.calendar.server.integration.name.api.NameType;

public record AnimeNameDO(NameType type, NameLanguage language, String name) {

}
