package de.derfrzocker.anime.calendar.server.model.domain.integration.linker;

import de.derfrzocker.anime.calendar.server.model.core.IntegrationAnimeId;
import java.util.List;

public record IntegrationNameIdData(IntegrationAnimeId integrationAnimeId, List<String> names) {

}
