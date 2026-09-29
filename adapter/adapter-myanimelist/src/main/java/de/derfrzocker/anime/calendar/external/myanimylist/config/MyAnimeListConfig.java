package de.derfrzocker.anime.calendar.external.myanimylist.config;

import io.smallrye.config.ConfigMapping;
import io.smallrye.config.WithName;

@ConfigMapping(prefix = "adapter.myanimelist")
public interface MyAnimeListConfig {

    @WithName("rest-client.client-id")
    String restClientClientId();

    @WithName("user-info.fetch-size")
    int userInfoFetchSize();
}
