package de.derfrzocker.anime.calendar.server.integration.api.linker;

import de.derfrzocker.anime.calendar.core.integration.IntegrationAnimeId;
import java.util.List;

public record IntegrationNameIdData(IntegrationAnimeId integrationAnimeId, List<String> names) {

}
