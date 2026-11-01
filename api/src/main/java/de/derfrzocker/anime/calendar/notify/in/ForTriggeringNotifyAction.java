package de.derfrzocker.anime.calendar.notify.in;

import de.derfrzocker.anime.calendar.RequestContext;
import de.derfrzocker.anime.calendar.notify.NotifyActionId;
import io.smallrye.mutiny.Uni;

public interface ForTriggeringNotifyAction {

    Uni<Void> trigger(NotifyActionId actionId, RequestContext context);
}
