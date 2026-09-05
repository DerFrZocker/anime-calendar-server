package de.derfrzocker.anime.calendar.integration.name.mongodb.data;

import de.derfrzocker.anime.calendar.integration.name.api.NameLanguage;
import de.derfrzocker.anime.calendar.integration.name.api.NameType;

public record AnimeNameDO(NameType type, NameLanguage language, String name) {

}
