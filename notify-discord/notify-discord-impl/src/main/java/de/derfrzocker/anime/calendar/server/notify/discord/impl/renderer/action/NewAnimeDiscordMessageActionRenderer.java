package de.derfrzocker.anime.calendar.server.notify.discord.impl.renderer.action;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.server.anime.api.NewAnimeNotificationAction;
import de.derfrzocker.anime.calendar.server.anime.exception.NewAnimeNotificationActionExceptions;
import de.derfrzocker.anime.calendar.server.anime.service.NewAnimeNotificationActionService;
import de.derfrzocker.anime.calendar.server.integration.service.IntegrationHelperService;
import de.derfrzocker.anime.calendar.server.notify.api.NotificationAction;
import de.derfrzocker.anime.calendar.server.notify.api.NotificationHolder;
import de.derfrzocker.anime.calendar.server.notify.discord.renderer.DiscordMessageActionRenderer;
import de.derfrzocker.anime.calendar.server.notify.discord.renderer.DiscordMessageBuilder;
import io.smallrye.common.annotation.Identifier;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
@Identifier(NewAnimeNotificationAction.NOTIFICATION_ACTION_TYPE_RAW)
public class NewAnimeDiscordMessageActionRenderer implements DiscordMessageActionRenderer {

    @Inject
    NewAnimeNotificationActionService newAnimeActionService;
    @Inject
    IntegrationHelperService integrationHelperService;

    @Override
    public void render(
            NotificationHolder holder,
            NotificationAction action,
            DiscordMessageBuilder builder,
            RequestContext context) {

        NewAnimeNotificationAction newAnime = toSpecificAction(action, context);

        if (!action.requireUserInput()) {
            String url = this.integrationHelperService.getUrl(newAnime.integrationId(), newAnime.integrationAnimeId());
            builder.addField(
                    "-> [%s] [%s] [%s] %s".formatted(
                            newAnime.integrationId().raw(),
                            newAnime.integrationAnimeId().raw(),
                            newAnime.score(),
                            newAnime.title()), url);

            builder.addButton(
                    "Create [%s] %s".formatted(newAnime.integrationId().raw(), newAnime.integrationAnimeId().raw()),
                    action.id().raw());
        } else {
            // Manuel link
            builder.addButton(
                    "Manual Link [%s] %s".formatted(
                            newAnime.sourceIntegrationId().raw(),
                            newAnime.sourceIntegrationAnimeId().raw()), action.id().raw());
        }
    }

    private NewAnimeNotificationAction toSpecificAction(NotificationAction action, RequestContext context) {
        return this.newAnimeActionService
                .getById(action.id(), context)
                .orElseThrow(NewAnimeNotificationActionExceptions.inconsistentNotFound(action.id()));
    }
}
