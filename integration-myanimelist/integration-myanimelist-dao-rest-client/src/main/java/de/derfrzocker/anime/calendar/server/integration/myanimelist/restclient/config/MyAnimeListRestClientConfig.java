package de.derfrzocker.anime.calendar.server.integration.myanimelist.restclient.config;

import io.smallrye.config.ConfigMapping;
import io.smallrye.config.WithName;

@ConfigMapping(prefix = "integration.myanimelist.rest-client")
public interface MyAnimeListRestClientConfig {

    @WithName("client-id")
    String xMalClientId();

    @WithName("user-list.size-limit")
    int userListSizeLimit();

    @WithName("season-info.size-limit")
    int seasonSizeLimit();
}
