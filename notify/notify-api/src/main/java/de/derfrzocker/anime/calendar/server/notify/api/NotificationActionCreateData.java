package de.derfrzocker.anime.calendar.server.notify.api;

import de.derfrzocker.anime.calendar.core.notify.NotificationId;

public record NotificationActionCreateData(NotificationId notificationId, NotificationActionType actionType) {

}
