package de.derfrzocker.anime.calendar.notify;

import io.smallrye.mutiny.Uni;

public interface NotifyActionInputFormRenderer {

    NotifyActionInputFormRenderer title(String title);

    NotifyActionInputFormRenderer addTextInput(String id, String label, int minLength, int maxLength);

    Uni<Void> render();
}
