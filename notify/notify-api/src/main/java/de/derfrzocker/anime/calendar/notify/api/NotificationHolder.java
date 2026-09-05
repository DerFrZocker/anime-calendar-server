package de.derfrzocker.anime.calendar.notify.api;

import java.util.List;

public record NotificationHolder(Notification notification, List<NotificationAction> actions) {

}
