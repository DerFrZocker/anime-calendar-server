package de.derfrzocker.anime.calendar.server.integration.myanimelist.restclient.dao;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.exception.UnexpectedException;
import de.derfrzocker.anime.calendar.core.integration.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.core.season.Season;
import de.derfrzocker.anime.calendar.server.integration.myanimelist.api.MyAnimeListSeasonInfo;
import de.derfrzocker.anime.calendar.server.integration.myanimelist.dao.MyAnimeListSeasonInfoDao;
import de.derfrzocker.anime.calendar.server.integration.myanimelist.restclient.client.MyAnimeListRestClient;
import de.derfrzocker.anime.calendar.server.integration.myanimelist.restclient.config.MyAnimeListRestClientConfig;
import de.derfrzocker.anime.calendar.server.integration.myanimelist.restclient.data.AnimeListDataTDO;
import de.derfrzocker.anime.calendar.server.integration.myanimelist.restclient.data.AnimeListNodeTDO;
import de.derfrzocker.anime.calendar.server.integration.myanimelist.restclient.data.AnimeListResponseTDO;
import de.derfrzocker.anime.calendar.server.integration.myanimelist.restclient.data.MyAnimeListSeason;
import de.derfrzocker.anime.calendar.server.integration.name.api.AnimeName;
import de.derfrzocker.anime.calendar.server.integration.name.api.NameLanguage;
import de.derfrzocker.anime.calendar.server.integration.name.api.NameType;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.time.Year;
import java.util.List;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.resteasy.reactive.RestResponse;

@ApplicationScoped
public class MyAnimeListSeasonInfoRestDaoImpl
        implements MyAnimeListSeasonInfoDao {

    private static final String WARN_LIMIT = "MyAnimeList season request for year '%s' and season '%s' returned '%s' " +
            "entries, which is the same (or more) than the config limit of '%s'.";

    private static final String UNEXPECTED
            = "Could not get MyAnimeList season info for year '%s' and season '%s' for, because of unexpected status " +
            "'%s'.";

    private static final NameType DEFAULT_NAME_TYPE = new NameType("main");
    private static final NameLanguage DEFAULT_NAME_LANGUAGE = new NameLanguage("x-jat");

    @RestClient
    MyAnimeListRestClient restClient;
    @Inject
    MyAnimeListRestClientConfig config;

    @Override
    public List<MyAnimeListSeasonInfo> getAllByYearAndSeason(Year year, Season season, RequestContext context) {
        RestResponse<AnimeListResponseTDO> response = getResponse(year, season);

        if (response.getStatus() != RestResponse.Status.OK.getStatusCode()) {
            throw UnexpectedException.from(UNEXPECTED.formatted(year.getValue(), season, response.getStatus())).get();
        }

        AnimeListResponseTDO entity = response.getEntity();
        if (entity.data().size() >= this.config.seasonSizeLimit()) {
            Log.warnf(WARN_LIMIT, year, season, entity.data().size(), this.config.seasonSizeLimit());
        }

        return entity.data().stream().map(AnimeListDataTDO::node).map(node -> map(year, season, node)).toList();
    }

    private RestResponse<AnimeListResponseTDO> getResponse(Year year, Season season) {
        return this.restClient.getSeasonAnime(
                year.getValue(),
                MyAnimeListSeason.from(season),
                this.config.seasonSizeLimit());
    }

    private MyAnimeListSeasonInfo map(Year year, Season season, AnimeListNodeTDO node) {
        IntegrationAnimeId animeId = new IntegrationAnimeId(String.valueOf(node.id()));
        AnimeName name = new AnimeName(DEFAULT_NAME_TYPE, DEFAULT_NAME_LANGUAGE, node.title());

        return new MyAnimeListSeasonInfo(animeId, year, season, name);
    }
}
