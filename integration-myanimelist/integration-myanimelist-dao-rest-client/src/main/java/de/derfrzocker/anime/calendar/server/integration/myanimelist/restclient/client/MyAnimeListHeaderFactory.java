package de.derfrzocker.anime.calendar.server.integration.myanimelist.restclient.client;

import de.derfrzocker.anime.calendar.server.integration.myanimelist.restclient.config.MyAnimeListRestClientConfig;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.MultivaluedHashMap;
import jakarta.ws.rs.core.MultivaluedMap;
import org.eclipse.microprofile.rest.client.ext.ClientHeadersFactory;

class MyAnimeListHeaderFactory implements ClientHeadersFactory {

    @Inject
    MyAnimeListRestClientConfig config;

    @Override
    public MultivaluedMap<String, String> update(MultivaluedMap<String, String> incomingHeaders,
                                                 MultivaluedMap<String, String> clientOutgoingHeaders) {
        MultivaluedMap<String, String> result = new MultivaluedHashMap<>();

        result.add("X-MAL-CLIENT-ID", this.config.xMalClientId());

        return result;
    }
}
