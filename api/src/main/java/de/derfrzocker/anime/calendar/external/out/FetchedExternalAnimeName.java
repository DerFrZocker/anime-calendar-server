package de.derfrzocker.anime.calendar.external.out;

import de.derfrzocker.anime.calendar.external.NameLanguage;
import de.derfrzocker.anime.calendar.external.NameType;

import java.util.List;

public record FetchedExternalAnimeName(
        NameType type, NameLanguage language, List<String> names) {

}
