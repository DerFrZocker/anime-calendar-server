package de.derfrzocker.anime.calendar.server;

import co.aikar.idb.DB;
import co.aikar.idb.Database;
import co.aikar.idb.DatabaseOptions;
import co.aikar.idb.PooledDatabaseOptions;
import de.derfrzocker.anime.calendar.server.calendar.CalendarBuilder;
import de.derfrzocker.anime.calendar.server.calendar.CalendarRequestHandler;
import de.derfrzocker.anime.calendar.server.provider.AnimeInfoProvider;
import de.derfrzocker.anime.calendar.server.provider.AnimeStreamProvider;
import de.derfrzocker.anime.calendar.server.provider.AnimeUserInfoProvider;
import de.derfrzocker.anime.calendar.server.provider.animeinfo.SqlAnimeInfoProvider;
import de.derfrzocker.anime.calendar.server.provider.stream.CrunchyrollStreamProvider;
import de.derfrzocker.anime.calendar.server.provider.userinfo.AnimeCalendarUserInfoProvider;
import de.derfrzocker.anime.calendar.server.provider.userinfo.ProxerUserInfoProvider;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        Configuration configuration = parseConfigurationFile(new File("config"));

        Map<String, AnimeStreamProvider> animeStreamProviderMap = new LinkedHashMap<>();
        Map<String, AnimeUserInfoProvider> animeUserInfoProviderMap = new LinkedHashMap<>();
        add(animeStreamProviderMap, new CrunchyrollStreamProvider());
        add(animeUserInfoProviderMap, new AnimeCalendarUserInfoProvider());
        if (configuration.proxerApiKey() != null) {
            add(animeUserInfoProviderMap, new ProxerUserInfoProvider(configuration.proxerApiKey()));
        }

        AnimeInfoProvider animeInfoProvider = new SqlAnimeInfoProvider(animeUserInfoProviderMap, animeStreamProviderMap);
        CalendarBuilder calendarBuilder = new CalendarBuilder(animeInfoProvider, animeUserInfoProviderMap);
        CalendarRequestHandler calendarRequestHandler = new CalendarRequestHandler(calendarBuilder);

        DatabaseOptions options = DatabaseOptions
                .builder()
                .mysql(configuration.databaseUsername(), configuration.databasePassword(), configuration.databaseName(), configuration.databaseHostAndPort())
                .useOptimizations(false)
                .dsn("mariadb://"+ configuration.databaseHostAndPort() + "/" + configuration.databaseName())
                .build();
        Database db = PooledDatabaseOptions.builder().options(options).createHikariDatabase();
        DB.setGlobalDatabase(db);

        calendarRequestHandler.startServer();
    }

    private static void add(Map<String, AnimeStreamProvider> animeStreamProviderMap, AnimeStreamProvider animeStreamProvider) {
        animeStreamProviderMap.put(animeStreamProvider.getProviderName(), animeStreamProvider);
    }

    private static void add(Map<String, AnimeUserInfoProvider> animeUserInfoProviderMap, AnimeUserInfoProvider animeUserInfoProvider) {
        animeUserInfoProviderMap.put(animeUserInfoProvider.getProviderName(), animeUserInfoProvider);
    }

    private static Configuration parseConfigurationFile(File file) throws IOException {
        Map<String, String> keyValuePairs = new LinkedHashMap<>();

        try (Stream<String> lines = Files.lines(file.toPath())) {
            lines.map(line -> line.split("=", 2)).forEach(value -> keyValuePairs.put(value[0], value[1]));
        }

        return new Configuration(keyValuePairs.get("database-username"), keyValuePairs.get("database-password"), keyValuePairs.get("database-name"), keyValuePairs.get("database-host-and-port"), keyValuePairs.get("proxer-api-key"));
    }
}
