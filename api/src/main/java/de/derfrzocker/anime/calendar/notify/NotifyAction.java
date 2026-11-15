package de.derfrzocker.anime.calendar.notify;

import de.derfrzocker.anime.calendar.actor.ActorId;
import de.derfrzocker.anime.calendar.metadata.SimpleMetadataHolder;
import java.time.Instant;
import java.util.Optional;

public interface NotifyAction<T extends NotifyData> extends SimpleMetadataHolder {

    NotifyActionId id();

    int priority();

    boolean requiresActorInput();

    NotifyActionType type();

    NotifyDataType dataType();

    T data();

    Optional<Instant> executedAt();

    Optional<ActorId> executedBy();
}
