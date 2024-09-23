package de.derfrzocker.anime.calendar.api.integration.linker;

import de.derfrzocker.anime.calendar.api.integration.IntegrationAnimeId;
import java.util.List;

public record IntegrationNameIdData(IntegrationAnimeId integrationAnimeId, List<String> names) {

}
