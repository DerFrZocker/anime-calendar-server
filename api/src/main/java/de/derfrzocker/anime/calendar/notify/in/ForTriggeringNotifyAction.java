package de.derfrzocker.anime.calendar.notify.in;

import de.derfrzocker.anime.calendar.RequestContext;
import de.derfrzocker.anime.calendar.notify.NotifyActionId;
import io.smallrye.mutiny.Uni;

import java.util.Optional;
import java.util.function.Function;

public interface ForTriggeringNotifyAction {

    Uni<Void> trigger(NotifyActionId actionId, RequestContext context);

    Uni<Void> trigger(
            NotifyActionId actionId,
            Function<String, Optional<String>> valueSupplier,
            RequestContext context);
}
