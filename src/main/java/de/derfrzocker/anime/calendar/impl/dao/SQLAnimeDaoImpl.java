package de.derfrzocker.anime.calendar.impl.dao;

import co.aikar.idb.Database;
import co.aikar.idb.DbRow;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import de.derfrzocker.anime.calendar.api.Anime;
import de.derfrzocker.anime.calendar.api.dao.AnimeDao;
import de.derfrzocker.anime.calendar.api.transformer.EpisodeTransformer;
import de.derfrzocker.anime.calendar.api.transformer.TransformerData;
import de.derfrzocker.anime.calendar.api.transformer.TransformerService;
import de.derfrzocker.anime.calendar.utils.JsonUtil;
import org.intellij.lang.annotations.Language;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SQLAnimeDaoImpl implements AnimeDao {

    @NotNull
    private final Database database;
    @NotNull
    private final TransformerService transformerService;

    public SQLAnimeDaoImpl(@NotNull Database database, @NotNull TransformerService transformerService) {
        this.database = database;
        this.transformerService = transformerService;
    }

    @Override
    public @Nullable Anime getAnime(int id) {
        @Language("SQL") String query = "CALL get_anime_by_id(?)";
        try {
            DbRow dbRow = database.getFirstRow(query, id);

            if (dbRow == null) {
                return null;
            }

            String animeName = dbRow.getString("animeName");
            int episodeCount = dbRow.getInt("episodeCount");
            JsonElement transformerConfigs = JsonParser.parseString(dbRow.getString("transformerConfigs"));

            if (!transformerConfigs.isJsonArray()) {
                throw new IllegalStateException("Expected transformer configs of type JsonArray but got " + transformerConfigs.getClass());
            }

            List<TransformerData<?>> transformerData = new ArrayList<>();
            for (JsonElement jsonElement : transformerConfigs.getAsJsonArray()) {
                if (!jsonElement.isJsonObject()) {
                    throw new IllegalStateException("Expected transformer config element of type JsonObject but got " + jsonElement.getClass());
                }

                String transformerKey = JsonUtil.getStringValue(jsonElement.getAsJsonObject(), "transformer-key");

                EpisodeTransformer<?> episodeTransformer = transformerService.getEpisodeTransformer(transformerKey);

                if (episodeTransformer == null) {
                    throw new IllegalStateException("EpisodeTransformer is null for key " + transformerKey);
                }

                JsonElement data = jsonElement.getAsJsonObject().get("transformer-data");

                if (data == null) {
                    throw new IllegalStateException("Data is null for key " + transformerKey);
                }

                transformerData.add(episodeTransformer.createTransformerData(data));
            }

            return new Anime(id, animeName, episodeCount, transformerData);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public @NotNull List<@NotNull Integer> getAnimeIds(@NotNull String userId) {
        @Language("SQL") String query = "CALL get_all_anime_ids_from_user_id(?)";
        try {
            List<DbRow> dbRows = database.getResults(query, userId);

            List<Integer> animeIds = new ArrayList<>();

            for (DbRow dbRow : dbRows) {
                animeIds.add(dbRow.getInt("animeId"));
            }
            return animeIds;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveAnimeIds(@NotNull String userId, @NotNull List<@NotNull Integer> integers) {
        @Language("SQL") String query = "CALL save_anime_id_to_user_id(?, ?)";
        try {
            for (int integer : integers) {
                database.executeUpdate(query, userId, integer);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
