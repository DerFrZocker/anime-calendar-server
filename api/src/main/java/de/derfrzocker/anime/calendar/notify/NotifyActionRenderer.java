package de.derfrzocker.anime.calendar.notify;

public interface NotifyActionRenderer<T extends NotifyData> {

    void render(NotifyAction<T> notifyAction);
}
