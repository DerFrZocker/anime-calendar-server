package de.derfrzocker.anime.calendar.server.model.domain;

import de.derfrzocker.anime.calendar.server.model.core.user.UserId;
import java.time.Instant;

public interface ModificationInfo {

    Instant createdAt();

    UserId createdBy();

    Instant updatedAt();

    UserId updatedBy();
}
