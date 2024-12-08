package de.derfrzocker.anime.calendar.server.mongodb.data;

import de.derfrzocker.anime.calendar.server.model.core.user.UserId;
import de.derfrzocker.anime.calendar.server.model.domain.ModificationInfo;
import java.time.Instant;

public class ModificationInfoDO {

    public Instant createdAt;
    public UserId createdBy;
    public Instant updatedAt;
    public UserId updatedBy;

    public void apply(ModificationInfo info) {
        this.createdAt = info.createdAt();
        this.createdBy = info.createdBy();
        this.updatedAt = info.updatedAt();
        this.updatedBy = info.updatedBy();
    }
}
