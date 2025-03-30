package de.derfrzocker.anime.calendar.server.rest.handler.user;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.anime.AnimeId;
import de.derfrzocker.anime.calendar.core.calendar.CalendarId;
import de.derfrzocker.anime.calendar.core.exception.ResourceNotFoundException;
import de.derfrzocker.anime.calendar.core.user.UserId;
import de.derfrzocker.anime.calendar.server.core.api.permission.UserPermissionService;
import de.derfrzocker.anime.calendar.server.core.api.user.UserService;
import de.derfrzocker.anime.calendar.server.impl.permission.change.PermissionRulePredicateAddOrChange;
import de.derfrzocker.anime.calendar.server.impl.permission.change.bi.PermissionActionRuleBiPredicateAddOrChange;
import de.derfrzocker.anime.calendar.server.impl.permission.predicate.FixedPredicate;
import de.derfrzocker.anime.calendar.server.impl.permission.predicate.SameUserIdPredicate;
import de.derfrzocker.anime.calendar.server.impl.permission.predicate.bi.SameActionAndUserIdBiPredicate;
import de.derfrzocker.anime.calendar.server.model.domain.permission.ObjectPermission;
import de.derfrzocker.anime.calendar.server.model.domain.permission.PermissionAction;
import de.derfrzocker.anime.calendar.server.model.domain.permission.PermissionType;
import de.derfrzocker.anime.calendar.server.model.domain.permission.UserPermissionCreateData;
import de.derfrzocker.anime.calendar.server.model.domain.permission.UserPermissionUpdateData;
import de.derfrzocker.anime.calendar.server.model.domain.user.CreatedUserHolder;
import de.derfrzocker.anime.calendar.server.model.domain.util.Change;
import de.derfrzocker.anime.calendar.server.model.domain.util.ChangeBuilder;
import de.derfrzocker.anime.calendar.server.rest.mapper.domain.UserDomain;
import de.derfrzocker.anime.calendar.server.rest.response.user.UserCreateResponse;
import de.derfrzocker.anime.calendar.server.rest.response.user.UserResponse;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import java.time.Instant;

@RequestScoped
public class UserRequestHandler {

    // TODO 2024-12-08: Move to better place
    private static final UserId USER_CREATE_SERVICE = new UserId("UCREATESER");

    private static final PermissionAction CALENDAR_CREATE_OWNER = new PermissionAction("calendar.create.owner");

    @Inject
    UserService userService;
    @Inject
    UserPermissionService permissionService;

    public UserResponse getById(UserId id, RequestContext context) {
        return this.userService.getById(id, context)
                               .map(UserDomain::toTransfer)
                               .map(UserResponse::new)
                               .orElseThrow(ResourceNotFoundException.with(id));
    }

    public UserCreateResponse create() {
        RequestContext context = new RequestContext(USER_CREATE_SERVICE, Instant.now());
        CreatedUserHolder createdUserHolder = this.userService.create(context);

        this.permissionService.createWithData(createdUserHolder.user().id(), new UserPermissionCreateData(), context);
        this.permissionService.updateWithData(createdUserHolder.user().id(),
                                              createDefaultPermission(createdUserHolder.user().id()),
                                              context);

        return new UserCreateResponse(createdUserHolder.token(), UserDomain.toTransfer(createdUserHolder.user()));
    }

    private UserPermissionUpdateData createDefaultPermission(UserId id) {
        return new UserPermissionUpdateData(createUserDefaultPermission(id),
                                            createCalendarDefaultPermission(id),
                                            createAnimeDefaultPermission(id),
                                            createCalendarAnimeLinkDefaultPermission(id));
    }

    private Change<ObjectPermission<UserId>> createUserDefaultPermission(UserId id) {
        return ChangeBuilder.<ObjectPermission<UserId>>builder()
                            .add(PermissionRulePredicateAddOrChange.of(PermissionType.READ,
                                                                       new SameUserIdPredicate(id)))
                            .add(PermissionActionRuleBiPredicateAddOrChange.of(new SameActionAndUserIdBiPredicate(
                                    CALENDAR_CREATE_OWNER,
                                    id)))
                            .build();
    }

    private Change<ObjectPermission<CalendarId>> createCalendarDefaultPermission(UserId id) {
        return ChangeBuilder.<ObjectPermission<CalendarId>>builder()
                            .add(PermissionRulePredicateAddOrChange.of(PermissionType.CREATE,
                                                                       new FixedPredicate<>(true)))
                            .build();
    }

    private Change<ObjectPermission<AnimeId>> createAnimeDefaultPermission(UserId id) {
        return ChangeBuilder.<ObjectPermission<AnimeId>>builder().add(Change.nothing()).build();
    }

    private Change<ObjectPermission<CalendarId>> createCalendarAnimeLinkDefaultPermission(UserId id) {
        return ChangeBuilder.<ObjectPermission<CalendarId>>builder().add(Change.nothing()).build();
    }
}
