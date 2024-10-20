package de.derfrzocker.anime.calendar.collect.anidb;

import java.util.List;

public record AnimeNames(ExternalAnimeId externalAnimeId, List<AnimeTitle> animeTitles) {
}
