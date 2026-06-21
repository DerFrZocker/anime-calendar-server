package de.derfrzocker.anime.calendar.server.notify.discord.impl.input;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.util.Change;
import de.derfrzocker.anime.calendar.server.anime.api.NewAnimeNotificationAction;
import de.derfrzocker.anime.calendar.server.anime.api.NewAnimeNotificationActionUpdateData;
import de.derfrzocker.anime.calendar.server.anime.service.NewAnimeNotificationActionService;
import de.derfrzocker.anime.calendar.server.notify.api.NotificationAction;
import de.derfrzocker.anime.calendar.server.notify.discord.input.DiscordInputApplier;
import de.derfrzocker.anime.calendar.server.notify.discord.input.DiscordInputValuesProvider;
import io.smallrye.common.annotation.Identifier;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
@Identifier(NewAnimeNotificationAction.NOTIFICATION_ACTION_TYPE_RAW)
public class NewAnimeDiscordInputApplier implements DiscordInputApplier {

    @Inject
    NewAnimeNotificationActionService actionService;

    @Override
    public void apply(NotificationAction action, DiscordInputValuesProvider provider, RequestContext context) {
        String title = provider.getInput("title");
        this.actionService.updateWithData(
                action.id(),
                new NewAnimeNotificationActionUpdateData(Change.to(title)),
                context);
    }
}
