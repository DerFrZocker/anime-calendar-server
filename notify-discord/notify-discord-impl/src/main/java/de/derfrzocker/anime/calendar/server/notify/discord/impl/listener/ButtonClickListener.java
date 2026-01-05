package de.derfrzocker.anime.calendar.server.notify.discord.impl.listener;

import static de.derfrzocker.anime.calendar.server.notify.exception.NotificationExceptions.inconsistentNotFound;
import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.notify.NotificationActionId;
import de.derfrzocker.anime.calendar.core.user.UserId;
import de.derfrzocker.anime.calendar.core.util.Change;
import de.derfrzocker.anime.calendar.server.notify.api.Notification;
import de.derfrzocker.anime.calendar.server.notify.api.NotificationAction;
import de.derfrzocker.anime.calendar.server.notify.api.NotificationActionType;
import de.derfrzocker.anime.calendar.server.notify.api.NotificationActionUpdateData;
import de.derfrzocker.anime.calendar.server.notify.discord.impl.config.DiscordConfig;
import de.derfrzocker.anime.calendar.server.notify.discord.impl.input.JDADiscordInputBuilderImpl;
import de.derfrzocker.anime.calendar.server.notify.discord.input.DiscordInputRenderer;
import de.derfrzocker.anime.calendar.server.notify.event.NotificationActionTriggerEvent;
import de.derfrzocker.anime.calendar.server.notify.service.NotificationActionService;
import de.derfrzocker.anime.calendar.server.notify.service.NotificationService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.enterprise.event.Observes;
import jakarta.enterprise.inject.Instance;
import jakarta.enterprise.inject.literal.NamedLiteral;
import jakarta.inject.Inject;
import java.time.Instant;
import java.util.Optional;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.interactions.components.buttons.ButtonInteraction;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import org.jboss.logging.Logger;

// TODO 2024-12-22: For better security in the future, we should link the discord account to a userid and check for
//  the users permission, this way we can also drag which user exactly made the change.
//  we should also use a random id and store the corresponding action on the server -> do not trust client data
//  this way we can also better invalidated old ids.
//  For now however it is fine as it is.
@ApplicationScoped
public class ButtonClickListener {

    private static final Logger LOG = Logger.getLogger(ButtonClickListener.class);

    private static final UserId LINKING_USER = new UserId("UDIANILINK");

    @Inject
    NotificationService notificationService;
    @Inject
    NotificationActionService actionService;
    @Inject
    Instance<DiscordInputRenderer> rendererInstance;
    @Inject
    Event<NotificationActionTriggerEvent> notificationActionTriggerEvent;
    @Inject
    JDA jda;
    @Inject
    DiscordConfig config;

    public void onButtonClicked(@Observes ButtonInteractionEvent event) {
        ButtonInteraction interaction = event.getInteraction();
        NotificationActionId customId = new NotificationActionId(interaction.getButton().getCustomId());
        RequestContext context = new RequestContext(LINKING_USER, Instant.now());

        Optional<NotificationAction> oAction = this.actionService.getById(customId, context);

        if (oAction.isEmpty()) {
            return;
        }

        NotificationAction action = oAction.get();
        if (action.executedAt() != null) {
            interaction.deferEdit().queue();
            return;
        }

        Notification notification = this.notificationService.getById(action.notificationId(), context)
                                                            .orElseThrow(inconsistentNotFound(action.notificationId()));
        if (Instant.now().isAfter(notification.validUntil())) {
            // TODO 2025-02-25: Should we remove it?
            interaction.deferEdit().queue();
            return;
        }

        if (action.requireUserInput()) {
            sendInputRequest(event, action, context);
            return;
        }

        interaction.deferEdit().queue();

        NotificationActionUpdateData updateData = new NotificationActionUpdateData(
                Change.to(context.requestTime()),
                Change.to(context.requestUser()));
        action = this.actionService.updateWithData(action.id(), updateData, context);

        this.notificationActionTriggerEvent.fire(new NotificationActionTriggerEvent(notification, action, context));
    }

    private void sendInputRequest(ButtonInteractionEvent event, NotificationAction action, RequestContext context) {
        DiscordInputRenderer renderer = selectRenderer(action.actionType());

        try {
            JDADiscordInputBuilderImpl builder = new JDADiscordInputBuilderImpl();

            renderer.render(action, builder, context);

            event.replyModal(builder.build(action)).queue();
        } catch (Exception e) {
            LOG.errorv(e, "Failed to send input request, for event '{}'.", event);
            trySendException(e);
        }
    }

    private DiscordInputRenderer selectRenderer(NotificationActionType type) {
        return this.rendererInstance.select(NamedLiteral.of(type.raw() + DiscordInputRenderer.NAME_SUFFIX)).get();
    }

    private void trySendException(Exception exception) {
        try {
            EmbedBuilder embed = new EmbedBuilder();
            embed.setTitle("Failed to send input request");
            embed.setDescription(exception.getMessage());

            MessageCreateBuilder builder = new MessageCreateBuilder();
            builder.setEmbeds(embed.build());

            this.jda.getTextChannelById(this.config.getChannelId()).sendMessage(builder.build()).queue();
        } catch (Exception e) {
            LOG.errorv("Could not send error message to Discord.", e);
        }
    }
}
