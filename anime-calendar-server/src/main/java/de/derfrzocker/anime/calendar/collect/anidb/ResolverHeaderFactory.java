package de.derfrzocker.anime.calendar.collect.anidb;

import jakarta.ws.rs.core.MultivaluedHashMap;
import jakarta.ws.rs.core.MultivaluedMap;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.ext.ClientHeadersFactory;

class ResolverHeaderFactory implements ClientHeadersFactory {

    @ConfigProperty(name = "anidb-name-resolver.user-agent")
    String userAgent;

    @Override
    public MultivaluedMap<String, String> update(MultivaluedMap<String, String> incomingHeaders, MultivaluedMap<String, String> clientOutgoingHeaders) {
        MultivaluedMap<String, String> result = new MultivaluedHashMap<>();
        System.out.println(userAgent);

        result.add("User-Agent", userAgent);

        return result;
    }
}
