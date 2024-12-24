package de.derfrzocker.anime.calendar.server.impl.notify.discord;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;
import org.javacord.api.event.interaction.ButtonClickEvent;
import org.javacord.api.event.message.MessageCreateEvent;

@ApplicationScoped
public class JavacordEventPublisher {

    @Inject
    Event<MessageCreateEvent> messageCreateEvent;
    @Inject
    Event<ButtonClickEvent> buttonClickEvent;

    public void fire(MessageCreateEvent event) {
        this.messageCreateEvent.fire(event);
    }

    public void fire(ButtonClickEvent event) {
        this.buttonClickEvent.fire(event);
    }
}
