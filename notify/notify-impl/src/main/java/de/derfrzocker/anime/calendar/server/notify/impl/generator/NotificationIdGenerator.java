package de.derfrzocker.anime.calendar.server.notify.impl.generator;

import de.derfrzocker.anime.calendar.generator.AbstractIdGenerator;
import de.derfrzocker.anime.calendar.server.model.core.notify.NotificationId;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class NotificationIdGenerator extends AbstractIdGenerator<NotificationId> {

    @Override
    protected NotificationId wrap(String raw) {
        return new NotificationId(raw);
    }

    @Override
    protected int idLength() {
        return NotificationId.ID_LENGTH;
    }

    @Override
    protected char idPrefix() {
        return NotificationId.ID_PREFIX;
    }
}
