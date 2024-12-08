package de.derfrzocker.anime.calendar.server.core.listener;

import de.derfrzocker.anime.calendar.server.core.api.user.UserService;
import de.derfrzocker.anime.calendar.server.model.domain.event.calendar.PostCalendarCreateEvent;
import de.derfrzocker.anime.calendar.server.model.domain.user.UserChangeData;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;

@RequestScoped
public class UserServiceListener {

    @Inject
    UserService userService;

    public void onPostCalendarCreate(@Observes PostCalendarCreateEvent event) {
        userService.updateWithChangeData(event.calendar().owner(), UserChangeData.addCalendar(event.calendar().id()));
    }
}
