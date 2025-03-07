package de.derfrzocker.anime.calendar.server.notify.impl.generator;

import de.derfrzocker.anime.calendar.generator.AbstractIdGenerator;
import de.derfrzocker.anime.calendar.server.model.core.notify.NotificationActionId;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class NotificationActionIdGenerator extends AbstractIdGenerator<NotificationActionId> {

    @Override
    protected NotificationActionId wrap(String raw) {
        return new NotificationActionId(raw);
    }

    @Override
    protected int idLength() {
        return NotificationActionId.ID_LENGTH;
    }

    @Override
    protected char idPrefix() {
        return NotificationActionId.ID_PREFIX;
    }
}
