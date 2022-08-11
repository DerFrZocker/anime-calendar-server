package de.derfrzocker.anime.calendar.server.provider.animeinfo;

import co.aikar.idb.DB;
import co.aikar.idb.DbRow;
import de.derfrzocker.anime.calendar.server.Anime;
import de.derfrzocker.anime.calendar.server.Episode;
import de.derfrzocker.anime.calendar.server.Region;
import de.derfrzocker.anime.calendar.server.RegionStream;
import de.derfrzocker.anime.calendar.server.provider.AnimeInfoProvider;
import de.derfrzocker.anime.calendar.server.provider.AnimeStreamProvider;
import de.derfrzocker.anime.calendar.server.provider.AnimeUserInfoProvider;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class SqlAnimeInfoProvider implements AnimeInfoProvider {
    private final Map<String, AnimeUserInfoProvider> userInfoProvider;
    private final Map<String, AnimeStreamProvider> streamProvider;

    public SqlAnimeInfoProvider(Map<String, AnimeUserInfoProvider> userInfoProvider, Map<String, AnimeStreamProvider> streamProvider) {
        this.userInfoProvider = userInfoProvider;
        this.streamProvider = streamProvider;
    }

    @Override
    public Anime getAnime(AnimeUserInfoProvider provider, String externId) {
        try {
            DbRow result = DB.getFirstRow("CALL get_anime_by_extern_id(?, ?)", provider.getProviderName(), Integer.parseInt(externId));

            if (result == null || result.isEmpty()) {
                return null;
            }

            int animeId = result.getInt("anime_id");
            String name = result.getString("name");

            Map<Region, String> regionNames = getRegionNames(animeId);
            Map<AnimeUserInfoProvider, String> externIds = getExternId(animeId);
            Map<AnimeStreamProvider, String> streamIds = getStreamId(animeId);
            List<Episode> episodes = getEpisodes(animeId);

            return new Anime(animeId, name, episodes, regionNames, externIds, streamIds);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Map<AnimeStreamProvider, String> getStreamId(int animeId) {
        Map<AnimeStreamProvider, String> streamIdMap = new LinkedHashMap<>();
        try {
            List<DbRow> externId = DB.getResults("CALL get_extern_stream_id_by_anime_id(?)", animeId);

            for (DbRow dbRow : externId) {
                streamIdMap.put(streamProvider.get(dbRow.getString("stream_provider")), dbRow.getString("stream_id"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return streamIdMap;
    }

    private Map<Region, String> getRegionNames(int animeId) {
        Map<Region, String> regionStringMap = new LinkedHashMap<>();
        try {
            List<DbRow> regionNames = DB.getResults("CALL get_region_names_from_anime_id(?)", animeId);

            for (DbRow dbRow : regionNames) {
                regionStringMap.put(Region.valueOf(dbRow.getString("region_name").toUpperCase()), dbRow.getString("name"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return regionStringMap;
    }

    private Map<AnimeUserInfoProvider, String> getExternId(int animeId) {
        Map<AnimeUserInfoProvider, String> externIdMap = new LinkedHashMap<>();
        try {
            List<DbRow> externId = DB.getResults("CALL get_extern_id_by_anime_id(?)", animeId);

            for (DbRow dbRow : externId) {
                externIdMap.put(userInfoProvider.get(dbRow.getString("provider_name")), dbRow.getString("extern_id"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return externIdMap;
    }

    private List<Episode> getEpisodes(int animeId) {
        List<Episode> episodes = new LinkedList<>();

        try {
            List<DbRow> episodeRows = DB.getResults("CALL get_episodes_by_anime_id(?)", animeId);

            for (DbRow dbRow : episodeRows) {
                int episodeId = dbRow.getInt("episode_id");
                int episodeNumber = dbRow.getInt("episode");
                int length = dbRow.getInt("length");
                LocalDateTime time = dbRow.get("airing_time");
                Map<Region, RegionStream> regionStreams = getRegionStream(episodeId);
                episodes.add(new Episode(episodeId, episodeNumber, length, time, regionStreams));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return episodes;
    }

    private Map<Region, RegionStream> getRegionStream(int episodeId) {
        Map<Region, RegionStream> regionStreams = new LinkedHashMap<>();

        try {
            List<DbRow> regionStreamRows = DB.getResults("CALL get_region_streams_from_episode_id(?)", episodeId);

            for (DbRow dbRow : regionStreamRows) {
                Region region = Region.valueOf(dbRow.getString("region_name").toUpperCase());
                AnimeStreamProvider stream = streamProvider.get(dbRow.getString("stream_provider_name"));
                LocalDateTime date = dbRow.get("release_date");

                regionStreams.put(region, new RegionStream(region, stream, date));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return regionStreams;
    }
}
