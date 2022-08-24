package de.derfrzocker.anime.calendar.server.provider.userinfo;

import de.derfrzocker.anime.calendar.server.Anime;
import de.derfrzocker.anime.calendar.server.provider.AnimeUserInfoProvider;
import me.proxer.library.ProxerApi;
import me.proxer.library.ProxerException;
import me.proxer.library.api.user.UserMediaListEndpoint;
import me.proxer.library.entity.user.UserMediaListEntry;
import me.proxer.library.enums.UserMediaListFilterType;
import org.jetbrains.annotations.NotNull;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;

public class ProxerUserInfoProvider implements AnimeUserInfoProvider {

    private final ProxerApi api;

    public ProxerUserInfoProvider(String apiKey) {
        api = new ProxerApi.Builder(apiKey).build();
    }

    @Override
    public String getProviderName() {
        return "proxer_me";
    }

    @Override
    public @NotNull List<String> getAnimeId(@NotNull String userId) {
        List<String> result = new LinkedList<>();
        UserMediaListEndpoint endpoint = api.user().mediaList(null, userId);
        try {
            List<UserMediaListEntry> watching = endpoint.filter(UserMediaListFilterType.WATCHING).build().execute();
            List<UserMediaListEntry> willWatch = endpoint.filter(UserMediaListFilterType.WILL_WATCH).build().execute();

            for (UserMediaListEntry entry : watching) {
                result.add(entry.getName());
            }
            for (UserMediaListEntry entry : willWatch) {
                result.add(entry.getName());
            }

            return result;
        } catch (ProxerException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public URI createAnimeLink(Anime anime) {
        try {
            return new URI("https://proxer.me/info/" + anime.externId().get(this));
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
