package de.derfrzocker.anime.calendar.notify.api;

import de.derfrzocker.anime.calendar.core.notify.NotificationType;
import java.time.Instant;

public record NotificationCreateData(NotificationType type, Instant validUntil) {

}
