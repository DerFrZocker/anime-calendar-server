package de.derfrzocker.anime.calendar.notify;

public interface NotifyRenderer<T extends NotifyData> {

    void render(Notify<T> notify);
}
