package de.derfrzocker.anime.calendar.server.notify.discord.impl.input;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.integration.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.core.util.Change;
import de.derfrzocker.anime.calendar.server.integration.api.ManualLinkNotificationActionUpdateData;
import de.derfrzocker.anime.calendar.server.integration.service.ManualLinkNotificationActionService;
import de.derfrzocker.anime.calendar.server.notify.api.NotificationAction;
import de.derfrzocker.anime.calendar.server.notify.discord.input.DiscordInputApplier;
import de.derfrzocker.anime.calendar.server.notify.discord.input.DiscordInputValuesProvider;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@ApplicationScoped
@Named("ManualLink" + DiscordInputApplier.NAME_SUFFIX)
public class ManualLinkDiscordInputApplier implements DiscordInputApplier {

    @Inject
    ManualLinkNotificationActionService actionService;

    @Override
    public void apply(NotificationAction action, DiscordInputValuesProvider provider, RequestContext context) {
        IntegrationAnimeId integrationAnimeId = new IntegrationAnimeId(provider.getInput("integrationAnimeId"));
        this.actionService.updateWithData(action.id(),
                                          new ManualLinkNotificationActionUpdateData(Change.to(integrationAnimeId)),
                                          context);
    }
}
