package de.derfrzocker.anime.calendar.notify.api;

import de.derfrzocker.anime.calendar.core.notify.NotificationType;
import java.time.Instant;

public record NotificationCreateData<T>(NotificationType type, Instant validUntil, T data) {

}
