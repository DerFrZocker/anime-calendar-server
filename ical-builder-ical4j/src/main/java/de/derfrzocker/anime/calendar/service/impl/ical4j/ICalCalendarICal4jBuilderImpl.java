package de.derfrzocker.anime.calendar.service.impl.ical4j;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.server.anime.api.Anime;
import de.derfrzocker.anime.calendar.server.episode.api.AnimeEpisodes;
import de.derfrzocker.anime.calendar.server.episode.api.Episode;
import de.derfrzocker.anime.calendar.server.core.api.ical.ICalCalendarBuilder;
import de.derfrzocker.anime.calendar.server.model.domain.ical.ICalCalendar;
import jakarta.enterprise.context.ApplicationScoped;
import java.time.Duration;
import java.time.Instant;
import java.time.Period;
import java.util.List;
import java.util.UUID;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.CalScale;
import net.fortuna.ical4j.model.property.Description;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.Uid;
import net.fortuna.ical4j.model.property.Version;

@ApplicationScoped
public class ICalCalendarICal4jBuilderImpl implements ICalCalendarBuilder {

    private final static String PROID = "-//Marvin (DerFrZocker)//anime calendar 2.0//DE";

    @Override
    public ICalCalendar build(List<AnimeEpisodes> episodes, RequestContext context) {
        Calendar calendar = new Calendar();
        calendar.add(new ProdId.Factory().createProperty(PROID));
        calendar.add(new Version.Factory().createProperty(Version.VALUE_2_0));
        calendar.add(new Version.Factory().createProperty(CalScale.VALUE_GREGORIAN));
        calendar.add(new Uid.Factory().createProperty(UUID.randomUUID().toString()));

        for (AnimeEpisodes animeEpisode : episodes) {
            addAnime(calendar, animeEpisode);
        }

        calendar.validate();
        return new ICalCalendar(calendar.toString());
    }

    private void addAnime(Calendar calendar, AnimeEpisodes animeEpisodes) {
        for (Episode episode : animeEpisodes.episodes()) {
            addEpisode(calendar, animeEpisodes.anime(), episode);
        }
    }

    private void addEpisode(Calendar calendar, Anime anime, Episode episode) {
        if (episode.streamingTime() == null) {
            return;
        }

        // Don't add episodeCount older than 14 days
        if (Instant.now().isAfter(episode.streamingTime().plus(Period.ofDays(14)))) {
            return;
        }

        if (episode.episodeLength() == 0) {
            return;
        }

        String title = anime.title();
        if (episode.episodeName() != null) {
            title = episode.episodeName();
        }

        StringBuilder summary = new StringBuilder(title);
        if (episode.episodeNumber() != null) {
            summary.append(" (");
            summary.append(episode.episodeNumber());

            if (episode.episodeNumbers() != null) {
                summary.append(" / ");
                summary.append(episode.episodeNumbers());
            }

            summary.append(")");
        }

        if (episode.type() != null) {
            summary.insert(0, "] ");
            summary.insert(0, episode.type().raw());
            summary.insert(0, "[");
        }

        StringBuilder description = new StringBuilder();
        if (episode.animeListLink() != null) {
            description.append("Anime: ");
            description.append(episode.animeListLink());
            if (episode.streamingLink() != null) {
                description.append("\n");
            }
        }

        if (episode.streamingLink() != null) {
            description.append("Stream: ");
            description.append(episode.streamingLink());
            if (episode.integrationLink() != null) {
                description.append("\n");
            }
        }

        if (episode.integrationLink() != null) {
            description.append("Link: ");
            description.append(episode.integrationLink());
        }

        VEvent calendarEntry = new VEvent(episode.streamingTime(),
                                          Duration.ofMinutes(episode.episodeLength()),
                                          summary.toString());
        calendarEntry.add(new Description(description.toString()));
        calendar.add(calendarEntry);
    }
}
