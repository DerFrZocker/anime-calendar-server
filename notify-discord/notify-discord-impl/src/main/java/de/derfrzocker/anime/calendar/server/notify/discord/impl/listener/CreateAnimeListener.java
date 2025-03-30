package de.derfrzocker.anime.calendar.server.notify.discord.impl.listener;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.integration.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.core.integration.IntegrationId;
import de.derfrzocker.anime.calendar.core.user.UserId;
import de.derfrzocker.anime.calendar.server.core.api.anime.AnimeService;
import de.derfrzocker.anime.calendar.server.integration.api.AnimeIntegrationLinkCreateData;
import de.derfrzocker.anime.calendar.server.integration.service.AnimeIntegrationLinkService;
import de.derfrzocker.anime.calendar.server.model.domain.anime.Anime;
import de.derfrzocker.anime.calendar.server.model.domain.anime.AnimeCreateData;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import java.time.Instant;
import java.util.List;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.interactions.components.buttons.ButtonInteraction;

// TODO 2024-12-27: For better security in the future, we should link the discord account to a userid and check for
//  the users permission, this way we can also drag which user exactly made the change.
//  we should also use a random id and store the corresponding action on the server -> do not trust client data
//  this way we can also better invalidated old ids.
//  For now however it is fine as it is.
@ApplicationScoped
public class CreateAnimeListener {

    private static final UserId CREATE_ANIME_USER = new UserId("UDIANICREA");

    @Inject
    AnimeService animeService;
    @Inject
    AnimeIntegrationLinkService linkService;

    public void onButtonClicked(@Observes ButtonInteractionEvent event) {
        ButtonInteraction interaction = event.getInteraction();
        String customId = interaction.getButton().getId();

        if (customId == null || !customId.startsWith("create$")) {
            return;
        }

        interaction.deferEdit().queue();

        String[] parts = customId.split("\\$");
        IntegrationId integrationId = new IntegrationId(parts[1]);
        IntegrationAnimeId integrationAnimeId = new IntegrationAnimeId(parts[2]);
        StringBuilder title = new StringBuilder(parts[3]);

        for (int i = 4; i < parts.length; i++) {
            title.append("$").append(parts[i]);
        }

        RequestContext context = new RequestContext(CREATE_ANIME_USER, Instant.now());

        // TODO 2024-12-27: Better episode count
        Anime anime = this.animeService.createWithData(new AnimeCreateData(title.toString(), 12, List.of()), context);
        this.linkService.createWithData(anime.id(),
                                        integrationId,
                                        integrationAnimeId,
                                        new AnimeIntegrationLinkCreateData(),
                                        context);
    }
}
