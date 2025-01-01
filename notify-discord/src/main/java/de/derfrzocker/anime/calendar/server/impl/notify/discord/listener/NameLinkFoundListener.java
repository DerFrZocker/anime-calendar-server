package de.derfrzocker.anime.calendar.server.impl.notify.discord.listener;

import de.derfrzocker.anime.calendar.server.model.core.integration.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.server.model.core.integration.IntegrationId;
import de.derfrzocker.anime.calendar.server.model.domain.event.name.PostNameLinkSearchEvent;
import de.derfrzocker.anime.calendar.server.model.domain.name.NameSearchResult;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.Channel;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.component.ActionRow;
import org.javacord.api.entity.message.component.Button;
import org.javacord.api.entity.message.component.ButtonStyle;
import org.javacord.api.entity.message.component.LowLevelComponent;
import org.javacord.api.entity.message.embed.EmbedBuilder;

@ApplicationScoped
public class NameLinkFoundListener {

    private static final IntegrationId MY_ANIME_LIST = new IntegrationId("myanimelist");
    private static final IntegrationId ANIDB = new IntegrationId("anidb");

    @Inject
    DiscordApi api;
    @ConfigProperty(name = "discord.bot.name-link.channel-id")
    String channelId;

    public void onNameLinkFound(@Observes PostNameLinkSearchEvent event) {
        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle("[%s] %s".formatted(event.anime().id().raw(), event.anime().title()))
             .setDescription("Found following links:");

        // TODO 2024-12-23: Account for message limits
        List<LowLevelComponent> buttons = new ArrayList<>();
        List<NameSearchResult> searchResults = new ArrayList<>();
        event.searchResults().forEach((key, value) -> searchResults.addAll(value));
        searchResults.sort(Comparator.comparingInt(NameSearchResult::score));
        for (NameSearchResult searchResult : searchResults) {
            IntegrationId integrationId = searchResult.animeNameHolder().integrationId();
            embed.addField("[%s] [%s] [%s] %s".formatted(integrationId.raw(),
                                                         searchResult.animeNameHolder().integrationAnimeId().raw(),
                                                         searchResult.score(),
                                                         searchResult.bestName().name()),
                           getUrl(integrationId, searchResult.animeNameHolder().integrationAnimeId()));
            if (buttons.size() >= 5) {
                continue;
            }

            buttons.add(Button.create("link$%s$%s$%s".formatted(event.anime().id().raw(),
                                                                integrationId.raw(),
                                                                searchResult.animeNameHolder()
                                                                            .integrationAnimeId()
                                                                            .raw()),
                                      ButtonStyle.PRIMARY,
                                      "Link [%s] %s".formatted(integrationId.raw(),
                                                               searchResult.animeNameHolder()
                                                                           .integrationAnimeId()
                                                                           .raw())));
        }

        // TODO 2024-12-23: Better error handling
        new MessageBuilder().addEmbed(embed)
                            .addComponents(ActionRow.of(buttons))
                            .send(this.api.getChannelById(this.channelId)
                                          .flatMap(Channel::asTextChannel)
                                          .orElseThrow());
    }

    private String getUrl(IntegrationId integrationId, IntegrationAnimeId integrationAnimeId) {
        if (MY_ANIME_LIST.equals(integrationId)) {
            return "https://myanimelist.net/anime/%s".formatted(integrationAnimeId.raw());
        }
        if (ANIDB.equals(integrationId)) {
            return "https://anidb.net/anime/%s".formatted(integrationAnimeId.raw());
        }

        // TODO 2024-12-23: Better exception
        throw new RuntimeException("Unknown integration id: " + integrationId);
    }
}
