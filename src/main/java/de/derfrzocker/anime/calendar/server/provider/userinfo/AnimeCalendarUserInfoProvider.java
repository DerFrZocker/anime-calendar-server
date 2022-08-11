package de.derfrzocker.anime.calendar.server.provider.userinfo;

import co.aikar.idb.DB;
import co.aikar.idb.DbRow;
import de.derfrzocker.anime.calendar.server.Anime;
import de.derfrzocker.anime.calendar.server.provider.AnimeUserInfoProvider;
import org.jetbrains.annotations.NotNull;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class AnimeCalendarUserInfoProvider implements AnimeUserInfoProvider {
    @Override
    public String getProviderName() {
        return "anime-calendar_com";
    }

    @Override
    public @NotNull List<String> getAnimeId(@NotNull String userId) {
        List<String> anime = new LinkedList<>();

        try {
            List<DbRow> animeRows = DB.getResults("CALL get_anime_calendar_user_anime_by_username(?)", userId);

            for (DbRow dbRow : animeRows) {
                anime.add(dbRow.getString("extern_id"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return anime;
    }

    @Override
    public URI createAnimeLink(Anime anime) {
        try {
            return new URI("https://www.anime-calendar.com/");
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
