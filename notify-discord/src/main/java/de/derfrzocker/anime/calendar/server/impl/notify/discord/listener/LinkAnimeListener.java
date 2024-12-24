package de.derfrzocker.anime.calendar.server.impl.notify.discord.listener;

import de.derfrzocker.anime.calendar.server.core.api.integration.AnimeIntegrationLinkService;
import de.derfrzocker.anime.calendar.server.model.core.anime.AnimeId;
import de.derfrzocker.anime.calendar.server.model.core.integration.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.server.model.core.integration.IntegrationId;
import de.derfrzocker.anime.calendar.server.model.core.user.UserId;
import de.derfrzocker.anime.calendar.server.model.domain.RequestContext;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import java.time.Instant;
import org.javacord.api.event.interaction.ButtonClickEvent;
import org.javacord.api.interaction.ButtonInteraction;

// TODO 2024-12-22: For better security in the future, we should link the discord account to a userid and check for
//  the users permission, this way we can also drag which user exactly made the change.
//  we should also use a random id and store the corresponding action on the server -> do not trust client data
//  this way we can also better invalidated old ids.
//  For now however it is fine as it is.
@ApplicationScoped
public class LinkAnimeListener {

    private static final UserId LINKING_USER = new UserId("UDIANILINK");

    @Inject
    AnimeIntegrationLinkService service;

    public void onButtonClicked(@Observes ButtonClickEvent event) {
        ButtonInteraction interaction = event.getButtonInteraction();
        String customId = interaction.getCustomId();

        if (!customId.startsWith("link$")) {
            return;
        }

        interaction.acknowledge();

        String[] parts = customId.split("\\$");
        AnimeId animeId = new AnimeId(parts[1]);
        IntegrationId integrationId = new IntegrationId(parts[2]);
        IntegrationAnimeId integrationAnimeId = new IntegrationAnimeId(parts[3]);

        this.service.createWithData(animeId,
                                    integrationId,
                                    integrationAnimeId,
                                    new RequestContext(LINKING_USER, Instant.now()));
    }
}
