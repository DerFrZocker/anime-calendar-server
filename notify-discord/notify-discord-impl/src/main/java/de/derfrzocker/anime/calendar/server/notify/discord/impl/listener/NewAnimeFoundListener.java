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
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.ItemComponent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.components.buttons.ButtonStyle;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
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
    JDA jda;
    @Inject
    DiscordConfig config;

    public void onNewAnimeFound(@Observes PostNewAnimeFoundEvent event) {
        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle("New anime found:");

        embed.addField("[%s] [%s] %s".formatted(event.fromIntegration().raw(),
                                                event.fromAnimeId().raw(),
                                                event.fromAnimeTitle()),
                       getUrl(event.fromIntegration(), event.fromAnimeId()),
                       false);


        // TODO 2024-12-23: Account for message limits
        List<ItemComponent> buttons = new ArrayList<>();
        List<NameSearchResult> results = new ArrayList<>(event.potentialNames());
        results.sort(Comparator.comparingInt(NameSearchResult::score));
        for (NameSearchResult name : results) {
            IntegrationId integrationId = name.animeNameHolder().integrationId();
            IntegrationAnimeId integrationAnimeId = name.animeNameHolder().integrationAnimeId();
            String title = name.bestName().name();
            embed.addField("[%s] [%s] [%s] %s".formatted(integrationId.raw(),
                                                         integrationAnimeId.raw(),
                                                         name.score(),
                                                         title), getUrl(integrationId, integrationAnimeId), false);

            if (buttons.size() >= 5) {
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

            buttons.add(Button.of(ButtonStyle.PRIMARY,
                                  "create$%s$%s$%s".formatted(event.fromIntegration().raw(),
                                                              event.fromAnimeId().raw(),
                                                              mainName.name()),
                                  "Create %s".formatted(title)));
        }

        MessageCreateBuilder builder = new MessageCreateBuilder().addEmbeds(embed.build());

        if (!buttons.isEmpty()) {
            builder.addComponents(ActionRow.of(buttons));
        }

        // TODO 2024-12-23: Better error handling
        this.jda.getTextChannelById(this.config.getChannelId()).sendMessage(builder.build()).queue();
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
