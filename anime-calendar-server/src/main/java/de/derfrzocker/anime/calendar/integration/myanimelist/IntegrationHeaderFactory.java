package de.derfrzocker.anime.calendar.integration.myanimelist;

import jakarta.ws.rs.core.MultivaluedHashMap;
import jakarta.ws.rs.core.MultivaluedMap;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.ext.ClientHeadersFactory;

class IntegrationHeaderFactory implements ClientHeadersFactory {

    @ConfigProperty(name = "my-anime-list-integration.client-id")
    String xMalClientId;

    @Override
    public MultivaluedMap<String, String> update(MultivaluedMap<String, String> incomingHeaders, MultivaluedMap<String, String> clientOutgoingHeaders) {
        MultivaluedMap<String, String> result = new MultivaluedHashMap<>();

        result.add("X-MAL-CLIENT-ID", xMalClientId);

        return result;
    }
}
