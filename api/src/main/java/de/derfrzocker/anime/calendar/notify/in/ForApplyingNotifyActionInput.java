package de.derfrzocker.anime.calendar.notify.in;

import de.derfrzocker.anime.calendar.notify.NotifyActionId;
import io.smallrye.mutiny.Uni;

public interface ForApplyingNotifyActionInput {

    Uni<Void> apply(NotifyActionId actionId);
}
