package de.derfrzocker.anime.calendar.server.notify.discord.impl.renderer;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.server.anime.api.NewAnimeNotification;
import de.derfrzocker.anime.calendar.server.anime.exception.NewAnimeNotificationExceptions;
import de.derfrzocker.anime.calendar.server.anime.service.NewAnimeNotificationService;
import de.derfrzocker.anime.calendar.server.integration.service.IntegrationHelperService;
import de.derfrzocker.anime.calendar.server.notify.api.Notification;
import de.derfrzocker.anime.calendar.server.notify.api.NotificationAction;
import de.derfrzocker.anime.calendar.server.notify.api.NotificationHolder;
import de.derfrzocker.anime.calendar.server.notify.discord.renderer.DiscordMessageActionRenderer;
import de.derfrzocker.anime.calendar.server.notify.discord.renderer.DiscordMessageBuilder;
import de.derfrzocker.anime.calendar.server.notify.discord.renderer.DiscordMessageRenderer;
import io.smallrye.common.annotation.Identifier;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Any;
import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@ApplicationScoped
@Named("NewAnime" + DiscordMessageRenderer.NAME_SUFFIX)
public class NewAnimeDiscordMessageRenderer implements DiscordMessageRenderer {

    @Inject
    NewAnimeNotificationService newAnimeNotificationService;
    @Inject
    IntegrationHelperService integrationHelperService;
    @Any
    @Inject
    Instance<DiscordMessageActionRenderer> actionRenderers;

    @Override
    public void render(NotificationHolder holder, DiscordMessageBuilder builder, RequestContext context) {
        NewAnimeNotification notification = toNotification(holder.notification(), context);

        if (holder.actions().isEmpty()) {
            // TODO 2025-02-23: Log better error here
            builder.setTitle("ERROR: Nothing found");
            return;
        }

        builder.setTitle("New Anime found:");

        String url = this.integrationHelperService.getUrl(
                notification.integrationId(),
                notification.integrationAnimeId());
        builder.addField(
                "From: %s - Id: %s".formatted(
                        notification.integrationId().raw(),
                        notification.integrationAnimeId().raw()), url);

        for (NotificationAction action : holder.actions()) {
            actionRenderers
                    .select(Identifier.Literal.of(action.actionType().raw()))
                    .get()
                    .render(holder, action, builder, context);
        }
    }

    private NewAnimeNotification toNotification(Notification notify, RequestContext context) {
        return this.newAnimeNotificationService
                .getById(notify.id(), context)
                .orElseThrow(NewAnimeNotificationExceptions.inconsistentNotFound(notify.id()));
    }
}
