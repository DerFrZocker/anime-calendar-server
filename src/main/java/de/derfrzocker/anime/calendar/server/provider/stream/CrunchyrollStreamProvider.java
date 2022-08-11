package de.derfrzocker.anime.calendar.server.provider.stream;

import de.derfrzocker.anime.calendar.server.Anime;
import de.derfrzocker.anime.calendar.server.Region;
import de.derfrzocker.anime.calendar.server.provider.AnimeStreamProvider;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class CrunchyrollStreamProvider implements AnimeStreamProvider {

    private final static String STREAM_LINK_FORMAT = "https://www.crunchyroll.com/%s/series/%s";

    @Override
    public String getProviderName() {
        return "crunchyroll_com";
    }

    @Override
    public URI createAnimeStreamLink(Anime anime, Region region) {
        try {
            return new URI(String.format(STREAM_LINK_FORMAT, parseRegion(region), anime.streamId().get(this)));
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private String parseRegion(Region region) {
        return switch (region) {
            case DE_DE -> "de";
        };
    }
}
