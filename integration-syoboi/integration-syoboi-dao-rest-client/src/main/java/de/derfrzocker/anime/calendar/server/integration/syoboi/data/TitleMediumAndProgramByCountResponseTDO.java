package de.derfrzocker.anime.calendar.server.integration.syoboi.data;

import java.util.Map;

public record TitleMediumAndProgramByCountResponseTDO(Map<String, ProvidedTIDDataTDO> Titles,
                                                      Map<String, ProgramByCountTDO> Programs) {

}
