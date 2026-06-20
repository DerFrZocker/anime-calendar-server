package de.derfrzocker.anime.calendar.server.notify.discord.impl.renderer;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.integration.IntegrationIds;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.IgnoreTIDDataNotificationAction;
import de.derfrzocker.anime.calendar.server.integration.syoboi.exception.IgnoreTIDDataNotificationActionExceptions;
import de.derfrzocker.anime.calendar.server.integration.syoboi.service.IgnoreTIDDataNotificationActionService;
import de.derfrzocker.anime.calendar.server.notify.api.NotificationAction;
import de.derfrzocker.anime.calendar.server.notify.api.NotificationHolder;
import de.derfrzocker.anime.calendar.server.notify.discord.renderer.DiscordMessageActionRenderer;
import de.derfrzocker.anime.calendar.server.notify.discord.renderer.DiscordMessageBuilder;
import io.smallrye.common.annotation.Identifier;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
@Identifier(IgnoreTIDDataNotificationAction.NOTIFICATION_ACTION_TYPE_RAW)
public class IgnoreTIDDataDiscordMessageActionRenderer implements DiscordMessageActionRenderer {

    @Inject
    IgnoreTIDDataNotificationActionService ignoreTIDDataActionService;

    @Override
    public void render(
            NotificationHolder holder,
            NotificationAction action,
            DiscordMessageBuilder builder,
            RequestContext context) {

        IgnoreTIDDataNotificationAction ignoreTIDDataAction = toSpecificAction(action, context);

        builder.addButton(
                "Ignore [%s] %s".formatted(IntegrationIds.SYOBOI.raw(), ignoreTIDDataAction.tid().raw()),
                action.id().raw());
    }

    private IgnoreTIDDataNotificationAction toSpecificAction(NotificationAction action, RequestContext context) {
        return this.ignoreTIDDataActionService
                .getById(action.id(), context)
                .orElseThrow(IgnoreTIDDataNotificationActionExceptions.inconsistentNotFound(action.id()));
    }
}
