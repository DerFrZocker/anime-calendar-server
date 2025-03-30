package de.derfrzocker.anime.calendar.server.notify.discord.impl.listener;

import de.derfrzocker.anime.calendar.core.integration.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.core.integration.IntegrationId;
import de.derfrzocker.anime.calendar.server.model.domain.event.anime.PostNewAnimeFoundEvent;
import de.derfrzocker.anime.calendar.server.model.domain.name.AnimeName;
import de.derfrzocker.anime.calendar.server.model.domain.name.NameLanguage;
import de.derfrzocker.anime.calendar.server.model.domain.name.NameSearchResult;
import de.derfrzocker.anime.calendar.server.model.domain.name.NameType;
import de.derfrzocker.anime.calendar.server.notify.discord.impl.config.DiscordConfig;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.Channel;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.component.ActionRow;
import org.javacord.api.entity.message.component.Button;
import org.javacord.api.entity.message.component.ButtonStyle;
import org.javacord.api.entity.message.component.LowLevelComponent;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.jboss.logging.Logger;

@ApplicationScoped
public class NewAnimeFoundListener {

    private static final Logger LOG = Logger.getLogger(NewAnimeFoundListener.class);

    private static final IntegrationId SYOBOI = new IntegrationId("syoboi");
    private static final IntegrationId MY_ANIME_LIST = new IntegrationId("myanimelist");
    private static final IntegrationId ANIDB = new IntegrationId("anidb");

    private static final NameType NAME_TYPE = new NameType("main");
    private static final NameLanguage NAME_LANGUAGE = new NameLanguage("x-jat");

    @Inject
    DiscordApi api;
    @Inject
    DiscordConfig config;

    public void onNewAnimeFound(@Observes PostNewAnimeFoundEvent event) {
        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle("New anime found:");

        embed.addField("[%s] [%s] %s".formatted(event.fromIntegration().raw(),
                                                event.fromAnimeId().raw(),
                                                event.fromAnimeTitle()),
                       getUrl(event.fromIntegration(), event.fromAnimeId()));


        // TODO 2024-12-23: Account for message limits
        List<LowLevelComponent> buttons = new ArrayList<>();
        List<NameSearchResult> results = new ArrayList<>(event.potentialNames());
        results.sort(Comparator.comparingInt(NameSearchResult::score));
        for (NameSearchResult name : results) {
            IntegrationId integrationId = name.animeNameHolder().integrationId();
            IntegrationAnimeId integrationAnimeId = name.animeNameHolder().integrationAnimeId();
            String title = name.bestName().name();
            embed.addField("[%s] [%s] [%s] %s".formatted(integrationId.raw(),
                                                         integrationAnimeId.raw(),
                                                         name.score(),
                                                         title), getUrl(integrationId, integrationAnimeId));

            if (buttons.size() > 5) {
                continue;
            }

            AnimeName mainName = null;
            for (AnimeName animeName : name.animeNameHolder().names()) {
                if (!NAME_LANGUAGE.equals(animeName.language())) {
                    continue;
                }

                if (!NAME_TYPE.equals(animeName.type())) {
                    continue;
                }

                mainName = animeName;
                break;
            }

            if (mainName == null) {
                LOG.warn("Cannot handle anime notification for event: " + event);
                return;
            }

            buttons.add(Button.create("create$%s$%s$%s".formatted(event.fromIntegration().raw(),
                                                                  event.fromAnimeId().raw(),
                                                                  mainName.name()),
                                      ButtonStyle.PRIMARY,
                                      "Create %s".formatted(title)));
        }

        // TODO 2024-12-23: Better error handling
        new MessageBuilder().addEmbed(embed)
                            .addComponents(ActionRow.of(buttons))
                            .send(this.api.getChannelById(this.config.getChannelId())
                                          .flatMap(Channel::asTextChannel)
                                          .orElseThrow());
    }

    private String getUrl(IntegrationId integrationId, IntegrationAnimeId integrationAnimeId) {
        if (SYOBOI.equals(integrationId)) {
            return "https://cal.syoboi.jp/tid/%s/time".formatted(integrationAnimeId.raw());
        }
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
