package de.derfrzocker.anime.calendar.external.myanimelist.client;

import de.derfrzocker.anime.calendar.external.myanimelist.config.MyAnimeListConfig;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.MultivaluedHashMap;
import jakarta.ws.rs.core.MultivaluedMap;
import org.eclipse.microprofile.rest.client.ext.ClientHeadersFactory;

class MyAnimeListHeaderFactory implements ClientHeadersFactory {

    private final MyAnimeListConfig config;

    @Inject
    MyAnimeListHeaderFactory(MyAnimeListConfig config) {
        this.config = config;
    }

    @Override
    public MultivaluedMap<String, String> update(
            MultivaluedMap<String, String> incomingHeaders,
            MultivaluedMap<String, String> clientOutgoingHeaders) {

        MultivaluedMap<String, String> result = new MultivaluedHashMap<>();

        result.add("X-MAL-CLIENT-ID", this.config.restClientClientId());

        return result;
    }
}
