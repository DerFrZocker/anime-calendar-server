package de.derfrzocker.anime.calendar.ical.renderer;

import de.derfrzocker.anime.calendar.anime.Episode;
import de.derfrzocker.anime.calendar.anime.EpisodeBuildOption;
import de.derfrzocker.anime.calendar.ical.out.ICalRenderer;
import java.util.UUID;

public class AnimeICalRenderer {

    private final static String PROID = "-//Marvin (DerFrZocker)//anime calendar 2.0//DE";

    private final EpisodeBuildOption buildOption;
    private final ICalRenderer renderer;

    private AnimeICalRenderer(EpisodeBuildOption buildOption, ICalRenderer renderer) {
        this.buildOption = buildOption;
        this.renderer = renderer;
    }

    public static AnimeICalRenderer with(EpisodeBuildOption buildOption, ICalRenderer renderer) {
        return new AnimeICalRenderer(buildOption, renderer).initHeader();
    }

    private AnimeICalRenderer initHeader() {

        renderer.prodId(PROID).uid(UUID.randomUUID().toString());

        return this;
    }

    public AnimeICalRenderer append(Episode episode) {
        renderer.event(event -> {
            StringBuilder summary = new StringBuilder();

            if (episode.streamType().isPresent()) {
                summary.append("[");
                summary.append(episode.streamType().get().raw());
                summary.append("] ");
            }

            summary.append(episode.name());

            if (episode.episode().isPresent()) {
                summary.append(" (");
                summary.append(episode.episode().get());

                if (episode.episodes().isPresent()) {
                    summary.append(" / ");
                    summary.append(episode.episodes().get());
                }

                summary.append(")");
            }

            StringBuilder description = new StringBuilder();
            if (episode.streamingLink().isPresent()) {
                description.append("Stream: ");
                description.append(episode.streamingLink().get());
                if (episode.animeListLink().isPresent()) {
                    description.append("\n");
                }
            }

            if (episode.animeListLink().isPresent()) {
                description.append("Link: ");
                description.append(episode.animeListLink().get());
            }

            event.start(episode.streamingTime());
            event.duration(episode.duration());
            event.summary(summary.toString());
            event.description(description.toString());
            // #3: Add stable UID, since Samsung Calendar import fails otherwise
            // (and per the specification the UID is required, meaning it was non-compliant before)
            event.uid(episode.animeId().raw() +
                      "-" +
                      episode.index() +
                      "-" +
                      Integer.toUnsignedString(buildOption.hashCode()) +
                      "@anime-calendar.com");
        });

        return this;
    }

    public String render() {
        return renderer.render();
    }
}
