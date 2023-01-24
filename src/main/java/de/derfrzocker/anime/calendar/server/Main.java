package de.derfrzocker.anime.calendar.server;

import co.aikar.idb.Database;
import co.aikar.idb.DatabaseOptions;
import co.aikar.idb.PooledDatabaseOptions;
import de.derfrzocker.anime.calendar.api.CalendarService;
import de.derfrzocker.anime.calendar.api.dao.AnimeDao;
import de.derfrzocker.anime.calendar.api.transformer.TransformerService;
import de.derfrzocker.anime.calendar.impl.BasicCalendarBuilder;
import de.derfrzocker.anime.calendar.impl.CalendarServiceImpl;
import de.derfrzocker.anime.calendar.impl.dao.SQLAnimeDaoImpl;
import de.derfrzocker.anime.calendar.impl.transformer.TransformerServiceImpl;
import de.derfrzocker.anime.calendar.impl.transformer.transformer.BaseStreamingTimeTransformer;
import de.derfrzocker.anime.calendar.impl.transformer.transformer.EpisodeLengthTransformer;
import de.derfrzocker.anime.calendar.impl.transformer.transformer.EpisodeNumberTransformer;
import de.derfrzocker.anime.calendar.impl.transformer.transformer.RegionNameTransformer;
import de.derfrzocker.anime.calendar.impl.transformer.transformer.RegionStreamingTimeTransformer;
import de.derfrzocker.anime.calendar.impl.transformer.transformer.RegionStreamingUrlTransformer;
import de.derfrzocker.anime.calendar.server.calendar.CalendarRequestHandler;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        Configuration configuration = parseConfigurationFile(new File("config.conf"));

        DatabaseOptions options = DatabaseOptions
                .builder()
                .mysql(configuration.databaseUsername(), configuration.databasePassword(), configuration.databaseName(), configuration.databaseHostAndPort())
                .useOptimizations(false)
                .dsn("mariadb://" + configuration.databaseHostAndPort() + "/" + configuration.databaseName())
                .build();
        Database database = PooledDatabaseOptions.builder().options(options).createHikariDatabase();

        TransformerService transformerService = new TransformerServiceImpl();
        transformerService.registerEpisodeTransformer(BaseStreamingTimeTransformer.INSTANCE);
        transformerService.registerEpisodeTransformer(EpisodeLengthTransformer.INSTANCE);
        transformerService.registerEpisodeTransformer(EpisodeNumberTransformer.INSTANCE);
        transformerService.registerEpisodeTransformer(RegionNameTransformer.INSTANCE);
        transformerService.registerEpisodeTransformer(RegionStreamingTimeTransformer.INSTANCE);
        transformerService.registerEpisodeTransformer(RegionStreamingUrlTransformer.INSTANCE);

        AnimeDao animeDao = new SQLAnimeDaoImpl(database, transformerService);


        CalendarService calendarService = new CalendarServiceImpl(animeDao, transformerService, new BasicCalendarBuilder());

        CalendarRequestHandler calendarRequestHandler = new CalendarRequestHandler(calendarService);

        calendarRequestHandler.startServer();
    }

    private static Configuration parseConfigurationFile(File file) throws IOException {
        Map<String, String> keyValuePairs = new LinkedHashMap<>();

        try (Stream<String> lines = Files.lines(file.toPath())) {
            lines.map(line -> line.split("=", 2)).forEach(value -> keyValuePairs.put(value[0], value[1]));
        }

        return new Configuration(keyValuePairs.get("database-username"), keyValuePairs.get("database-password"), keyValuePairs.get("database-name"), keyValuePairs.get("database-host-and-port"), keyValuePairs.get("proxer-api-key"));
    }
}
