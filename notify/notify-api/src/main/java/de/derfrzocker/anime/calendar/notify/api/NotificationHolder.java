package de.derfrzocker.anime.calendar.notify.api;

import java.util.List;

public record NotificationHolder<T>(Notification<T> notification, List<NotificationAction<?>> actions) {

}
