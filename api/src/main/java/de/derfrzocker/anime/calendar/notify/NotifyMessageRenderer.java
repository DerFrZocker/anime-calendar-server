package de.derfrzocker.anime.calendar.notify;

import io.smallrye.mutiny.Uni;

public interface NotifyMessageRenderer {

    NotifyMessageRenderer title(String title);

    NotifyMessageRenderer description(String description);

    NotifyMessageRenderer addField(String title, String description);

    NotifyMessageRenderer addButton(NotifyActionId actionId, String title);

    Uni<Void> render();
}
