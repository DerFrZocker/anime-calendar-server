package de.derfrzocker.anime.calendar.server.core.impl.user;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.user.UserId;
import de.derfrzocker.anime.calendar.server.model.domain.event.user.PostUserCreateEvent;
import de.derfrzocker.anime.calendar.server.model.domain.event.user.PostUserUpdateEvent;
import de.derfrzocker.anime.calendar.server.model.domain.event.user.PreUserCreateEvent;
import de.derfrzocker.anime.calendar.server.model.domain.event.user.PreUserUpdateEvent;
import de.derfrzocker.anime.calendar.server.model.domain.user.User;
import de.derfrzocker.anime.calendar.server.model.domain.user.UserUpdateData;
import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;

@Dependent
public class UserEventPublisher {

    @Inject
    Event<PreUserCreateEvent> preCreateEvent;
    @Inject
    Event<PostUserCreateEvent> postCreateEvent;

    @Inject
    Event<PreUserUpdateEvent> preUpdateEvent;
    @Inject
    Event<PostUserUpdateEvent> postUpdateEvent;

    public void firePreCreateEvent(User user, RequestContext context) {
        this.preCreateEvent.fire(new PreUserCreateEvent(user, context));
    }

    public void firePostCreateEvent(User user, RequestContext context) {
        this.postCreateEvent.fire(new PostUserCreateEvent(user, context));
    }

    public void firePreUpdateEvent(UserId id,
                                   UserUpdateData updateData,
                                   User current,
                                   User updated,
                                   RequestContext context) {

        this.preUpdateEvent.fire(new PreUserUpdateEvent(id, updateData, current, updated, context));
    }

    public void firePostUpdateEvent(UserId id,
                                    UserUpdateData updateData,
                                    User current,
                                    User updated,
                                    RequestContext context) {

        this.postUpdateEvent.fire(new PostUserUpdateEvent(id, updateData, current, updated, context));
    }
}
