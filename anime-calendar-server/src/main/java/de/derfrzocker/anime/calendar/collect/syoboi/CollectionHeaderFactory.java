package de.derfrzocker.anime.calendar.collect.syoboi;

import jakarta.ws.rs.core.MultivaluedHashMap;
import jakarta.ws.rs.core.MultivaluedMap;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.ext.ClientHeadersFactory;

class CollectionHeaderFactory implements ClientHeadersFactory {

    @ConfigProperty(name = "syoboi-collection.user-agent")
    String userAgent;

    @Override
    public MultivaluedMap<String, String> update(MultivaluedMap<String, String> incomingHeaders, MultivaluedMap<String, String> clientOutgoingHeaders) {
        MultivaluedMap<String, String> result = new MultivaluedHashMap<>();

        result.add("User-Agent", userAgent);

        return result;
    }
}
