package de.derfrzocker.anime.calendar.server.integration.syoboi.data;

import java.util.Map;

public record TitleMediumAndProgramResponseTDO(Map<String, ProvidedTIDDataTDO> Titles,
                                               Map<String, ProvidedAnimeScheduleTDO> Programs) {

}
