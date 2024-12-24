package de.derfrzocker.anime.calendar.server.rest.handler.calendar;

import de.derfrzocker.anime.calendar.server.core.api.calendar.CalendarService;
import de.derfrzocker.anime.calendar.server.core.api.permission.UserPermissionService;
import de.derfrzocker.anime.calendar.server.impl.permission.change.PermissionRulePredicateAddOrChange;
import de.derfrzocker.anime.calendar.server.impl.permission.change.bi.PermissionActionRuleBiPredicateAddOrChange;
import de.derfrzocker.anime.calendar.server.impl.permission.predicate.SameCalendarIdPredicate;
import de.derfrzocker.anime.calendar.server.impl.permission.predicate.bi.SameActionBiPredicate;
import de.derfrzocker.anime.calendar.server.model.core.anime.AnimeId;
import de.derfrzocker.anime.calendar.server.model.core.calendar.CalendarId;
import de.derfrzocker.anime.calendar.server.model.domain.RequestContext;
import de.derfrzocker.anime.calendar.server.model.domain.calendar.Calendar;
import de.derfrzocker.anime.calendar.server.model.domain.exception.ResourceNotFoundException;
import de.derfrzocker.anime.calendar.server.model.domain.permission.ObjectPermission;
import de.derfrzocker.anime.calendar.server.model.domain.permission.PermissionAction;
import de.derfrzocker.anime.calendar.server.model.domain.permission.PermissionType;
import de.derfrzocker.anime.calendar.server.model.domain.permission.UserPermissionUpdateData;
import de.derfrzocker.anime.calendar.server.model.domain.util.Change;
import de.derfrzocker.anime.calendar.server.model.domain.util.ChangeBuilder;
import de.derfrzocker.anime.calendar.server.rest.mapper.domain.CalendarDomain;
import de.derfrzocker.anime.calendar.server.rest.mapper.transfer.CalendarTransfer;
import de.derfrzocker.anime.calendar.server.rest.request.calendar.CalendarCreateRequest;
import de.derfrzocker.anime.calendar.server.rest.response.calendar.CalendarResponse;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

@RequestScoped
public class CalendarRequestHandler {

    @Inject
    CalendarService service;
    @Inject
    UserPermissionService permissionService;

    private static final PermissionAction CALENDAR_ANIME_LINK_CREATE_OR_UPDATE_ANIME_ID = new PermissionAction(
            "calendar-anime-link.create-or-update.anime-id");

    public CalendarResponse getById(CalendarId id, RequestContext context) {
        return this.service.getById(id, context).map(this::toResponse).orElseThrow(ResourceNotFoundException.with(id));
    }

    public CalendarResponse createWithData(CalendarCreateRequest request, RequestContext context) {
        Calendar calendar = this.service.createWithData(CalendarTransfer.toDomain(request.calendar()), context);

        this.permissionService.updateWithData(calendar.owner(), createDefaultPermission(calendar.id()), context);

        return toResponse(calendar);
    }

    private CalendarResponse toResponse(Calendar domain) {
        return new CalendarResponse(CalendarDomain.toTransfer(domain));
    }

    private UserPermissionUpdateData createDefaultPermission(CalendarId id) {
        return new UserPermissionUpdateData(Change.nothing(),
                                            createCalendarDefaultPermission(id),
                                            createAnimeDefaultPermission(id),
                                            createCalendarAnimeLinkDefaultPermission(id));
    }

    private Change<ObjectPermission<CalendarId>> createCalendarDefaultPermission(CalendarId id) {
        return ChangeBuilder.<ObjectPermission<CalendarId>>builder()
                            .add(PermissionRulePredicateAddOrChange.of(PermissionType.READ,
                                                                       new SameCalendarIdPredicate(id)))
                            .add(PermissionRulePredicateAddOrChange.of(PermissionType.UPDATE,
                                                                       new SameCalendarIdPredicate(id)))
                            .add(PermissionRulePredicateAddOrChange.of(PermissionType.DELETE,
                                                                       new SameCalendarIdPredicate(id)))
                            .build();
    }

    private Change<ObjectPermission<AnimeId>> createAnimeDefaultPermission(CalendarId id) {
        return ChangeBuilder.<ObjectPermission<AnimeId>>builder()
                            .add(PermissionActionRuleBiPredicateAddOrChange.of(new SameActionBiPredicate<>(
                                    CALENDAR_ANIME_LINK_CREATE_OR_UPDATE_ANIME_ID)))
                            .build();
    }

    private Change<ObjectPermission<CalendarId>> createCalendarAnimeLinkDefaultPermission(CalendarId id) {
        return ChangeBuilder.<ObjectPermission<CalendarId>>builder()
                            .add(PermissionRulePredicateAddOrChange.of(PermissionType.CREATE,
                                                                       new SameCalendarIdPredicate(id)))
                            .add(PermissionRulePredicateAddOrChange.of(PermissionType.READ,
                                                                       new SameCalendarIdPredicate(id)))
                            .add(PermissionRulePredicateAddOrChange.of(PermissionType.UPDATE,
                                                                       new SameCalendarIdPredicate(id)))
                            .add(PermissionRulePredicateAddOrChange.of(PermissionType.DELETE,
                                                                       new SameCalendarIdPredicate(id)))
                            .build();
    }
}
