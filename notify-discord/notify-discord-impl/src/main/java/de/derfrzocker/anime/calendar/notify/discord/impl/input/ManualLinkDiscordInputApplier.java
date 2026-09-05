package de.derfrzocker.anime.calendar.notify.discord.impl.input;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.integration.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.core.util.Change;
import de.derfrzocker.anime.calendar.integration.api.ManualLinkNotificationAction;
import de.derfrzocker.anime.calendar.integration.api.ManualLinkNotificationActionUpdateData;
import de.derfrzocker.anime.calendar.integration.service.ManualLinkNotificationActionService;
import de.derfrzocker.anime.calendar.notify.api.NotificationAction;
import de.derfrzocker.anime.calendar.notify.discord.input.DiscordInputApplier;
import de.derfrzocker.anime.calendar.notify.discord.input.DiscordInputValuesProvider;
import io.smallrye.common.annotation.Identifier;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
@Identifier(ManualLinkNotificationAction.NOTIFICATION_ACTION_TYPE_RAW)
public class ManualLinkDiscordInputApplier implements DiscordInputApplier {

    @Inject
    ManualLinkNotificationActionService actionService;

    @Override
    public void apply(NotificationAction action, DiscordInputValuesProvider provider, RequestContext context) {
        IntegrationAnimeId integrationAnimeId = new IntegrationAnimeId(provider.getInput("integrationAnimeId"));
        this.actionService.updateWithData(
                action.id(),
                new ManualLinkNotificationActionUpdateData(Change.to(integrationAnimeId)),
                context);
    }
}
