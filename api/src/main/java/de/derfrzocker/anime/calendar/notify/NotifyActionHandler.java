package de.derfrzocker.anime.calendar.notify;

import io.smallrye.mutiny.Uni;

public interface NotifyActionHandler {

    Uni<Void> handle(NotifyAction<?> action);
}
