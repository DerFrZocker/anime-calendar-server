package de.derfrzocker.anime.calendar.server.calendar;

import de.derfrzocker.anime.calendar.server.Anime;
import de.derfrzocker.anime.calendar.server.Episode;
import de.derfrzocker.anime.calendar.server.Region;
import de.derfrzocker.anime.calendar.server.RegionStream;
import de.derfrzocker.anime.calendar.server.provider.AnimeInfoProvider;
import de.derfrzocker.anime.calendar.server.provider.AnimeUserInfoProvider;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.CalScale;
import net.fortuna.ical4j.model.property.Description;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.Uid;
import net.fortuna.ical4j.model.property.Version;

import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.Clock;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.TemporalAmount;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class CalendarBuilder {

    private final static String PROID = "-//Marvin (DerFrZocker)//anime calendar 1.0//DE";
    private final static int PROID_HASH = PROID.hashCode();

    private final AnimeInfoProvider infoProvider;
    private final Map<String, AnimeUserInfoProvider> userInfoProvider;

    public CalendarBuilder(AnimeInfoProvider infoProvider, Map<String, AnimeUserInfoProvider> userInfoProvider) {
        this.infoProvider = infoProvider;
        this.userInfoProvider = userInfoProvider;
    }

    public Calendar getCalendar(URI uri) throws ParameterException {
        CalendarOptions calendarOptions = parseParameters(uri.getRawQuery());

        List<String> animeIds = calendarOptions.userInfoProvider().getAnimeId(calendarOptions.userId());
        List<Anime> animeList = new ArrayList<>();

        for (String animeId : animeIds) {
            Anime anime = infoProvider.getAnime(calendarOptions.userInfoProvider(), animeId);
            if (anime != null) {
                animeList.add(anime);
            }
        }

        Calendar calendar = new Calendar();
        calendar.add(new ProdId(PROID));
        calendar.add(new Version.Factory().createProperty(Version.VALUE_2_0));
        calendar.add(new Version.Factory().createProperty(CalScale.VALUE_GREGORIAN));
        calendar.add(createUid(PROID_HASH, uri.getRawQuery().hashCode(), calendarOptions.userInfoProvider().getProviderName().hashCode(), calendarOptions.userId().hashCode()));
        addAnime(calendar, calendarOptions, animeList);

        calendar.validate();
        return calendar;
    }

    private void addAnime(Calendar calendar, CalendarOptions calendarOptions, List<Anime> animeList) {
        for (Anime anime : animeList) {
            addAnime(calendar, calendarOptions, anime);
        }
    }

    private void addAnime(Calendar calendar, CalendarOptions calendarOptions, Anime anime) {
        for (Episode episode : anime.episodes()) {
            addEpisode(calendar, calendarOptions, anime, episode);
        }
    }

    private void addEpisode(Calendar calendar, CalendarOptions calendarOptions, Anime anime, Episode episode) {
        RegionStream regionStream = episode.regionStreams().get(calendarOptions.region());

        LocalDateTime start;
        if (calendarOptions.japanAiringTime() || regionStream == null) {
            start = episode.airingTime();
        } else {
            start = regionStream.releaseDate();
        }

        // No start time no calendar entry
        if (start == null) {
            return;
        }

        // If the time is more than seven days in the past, don't include it
        if (!start.isAfter(LocalDateTime.now(Clock.systemUTC()).minusDays(14))) {
            return;
        }

        TemporalAmount duration = Duration.ofMinutes(episode.length());

        String name = anime.regionName().get(calendarOptions.region());
        if (name == null) {
            name = anime.name();
        }

        VEvent calendarEntry = new VEvent(start.toInstant(ZoneOffset.UTC), duration, name + ": " + episode.episodeNumber());
        calendarEntry.add(createUid(PROID_HASH, calendarOptions.rawParameters().hashCode(), anime.animeId(), episode.episodeId()));
        if (regionStream != null) {
            calendarEntry.add(new Description("Anime: " + calendarOptions.userInfoProvider().createAnimeLink(anime) + "\nStream: " + regionStream.streamProvider().createAnimeStreamLink(anime, regionStream.region())));
        } else {
            calendarEntry.add(new Description("Anime: " + calendarOptions.userInfoProvider().createAnimeLink(anime)));
        }
        calendar.add(calendarEntry);
    }

    private CalendarOptions parseParameters(String parameters) throws ParameterException {
        Map<String, String> parametersMap = new HashMap<>();

        if (parameters == null) {
            return parseParameters(parameters, parametersMap);
        }

        String[] args = parameters.split("&");

        for (String arg : args) {
            String[] keyValue = arg.split("=");
            String key = URLDecoder.decode(keyValue[0], StandardCharsets.UTF_8);
            String value = "";

            if (keyValue.length > 1) {
                value = URLDecoder.decode(keyValue[1], StandardCharsets.UTF_8);
            }
            parametersMap.put(key, value);
        }

        return parseParameters(parameters, parametersMap);
    }

    private CalendarOptions parseParameters(String rawParameters, Map<String, String> parameters) throws ParameterException {
        AnimeUserInfoProvider userProvider = checkNotNull(userInfoProvider.get(getOrThrow(parameters, "user_provider")), "user_provider");
        String userId = getOrThrow(parameters, "user_id");
        Region region = Region.DE_DE;

        try {
            region = Region.valueOf(parameters.get("region").toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException | NullPointerException ignore) {
        }

        boolean japanAiringTime = !"local".equals(parameters.get("airing_time")); // Default Japan time

        return new CalendarOptions(rawParameters, userProvider, userId, region, japanAiringTime);
    }

    private <V> V checkNotNull(V toCheck, String key) throws InvalidParameterException {
        if (toCheck == null) {
            throw new InvalidParameterException(key);
        }

        return toCheck;
    }

    private String getOrThrow(Map<String, String> parameters, String key) throws MissingParameterException {
        String value = parameters.get(key);
        if (value == null) {
            throw new MissingParameterException(key);
        }

        return value;
    }

    private Uid createUid(int first, int second, int third, int fourth) {
        long most = Math.abs(first);
        most = (most << 32) | Math.abs(second);
        long least = Math.abs(third);
        least = (least << 32) | Math.abs(fourth);

        return new Uid(new UUID(most, least).toString());
    }
}
