package de.derfrzocker.anime.calendar.server.notify.api;

import java.time.Instant;

public record NotificationCreateData(NotificationType type, Instant validUntil) {

}
