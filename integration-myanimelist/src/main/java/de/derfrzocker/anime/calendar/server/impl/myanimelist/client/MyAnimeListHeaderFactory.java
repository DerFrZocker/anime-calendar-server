package de.derfrzocker.anime.calendar.server.impl.myanimelist.client;

import jakarta.ws.rs.core.MultivaluedHashMap;
import jakarta.ws.rs.core.MultivaluedMap;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.ext.ClientHeadersFactory;

class MyAnimeListHeaderFactory implements ClientHeadersFactory {

    @ConfigProperty(name = "myanimelist.name-and-season-update.client.client-id")
    String xMalClientId;

    @Override
    public MultivaluedMap<String, String> update(MultivaluedMap<String, String> incomingHeaders,
                                                 MultivaluedMap<String, String> clientOutgoingHeaders) {
        MultivaluedMap<String, String> result = new MultivaluedHashMap<>();

        result.add("X-MAL-CLIENT-ID", xMalClientId);

        return result;
    }
}
