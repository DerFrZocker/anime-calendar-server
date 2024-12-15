package de.derfrzocker.anime.calendar.server.mongodb.mapper.data;

import de.derfrzocker.anime.calendar.server.core.api.permission.PredicateParserService;
import de.derfrzocker.anime.calendar.server.model.domain.permission.ObjectPermission;
import de.derfrzocker.anime.calendar.server.model.domain.permission.PermissionActionRule;
import de.derfrzocker.anime.calendar.server.model.domain.permission.PermissionRule;
import de.derfrzocker.anime.calendar.server.model.domain.permission.PermissionType;
import de.derfrzocker.anime.calendar.server.model.domain.permission.UserPermission;
import de.derfrzocker.anime.calendar.server.mongodb.data.permission.ObjectPermissionDO;
import de.derfrzocker.anime.calendar.server.mongodb.data.permission.PermissionActionRuleDO;
import de.derfrzocker.anime.calendar.server.mongodb.data.permission.PermissionRuleDO;
import de.derfrzocker.anime.calendar.server.mongodb.data.permission.UserPermissionDO;
import java.util.HashMap;
import java.util.Map;

public final class UserPermissionData {

    public static UserPermission toDomain(UserPermissionDO data, PredicateParserService parserService) {
        return new UserPermission(data.id,
                                  data.createdAt,
                                  data.createdBy,
                                  data.updatedAt,
                                  data.updatedBy,
                                  toDomain(data.user, parserService),
                                  toDomain(data.calendar, parserService),
                                  toDomain(data.anime, parserService),
                                  toDomain(data.calendarAnimeLink, parserService));
    }

    private static <T> ObjectPermission<T> toDomain(ObjectPermissionDO data, PredicateParserService parserService) {
        if (data == null) {
            return null;
        }

        return new ObjectPermission<>(toDomain(data.permissionRules(), parserService),
                                      toDomain(data.actionRules(), parserService));
    }

    private static <T> Map<PermissionType, PermissionRule<T>> toDomain(Map<String, PermissionRuleDO> data,
                                                                       PredicateParserService parserService) {
        Map<PermissionType, PermissionRule<T>> result = new HashMap<>();

        for (Map.Entry<String, PermissionRuleDO> entry : data.entrySet()) {
            PermissionType type = PermissionType.valueOf(entry.getKey());
            PermissionRuleDO value = entry.getValue();
            if (value == null) {
                result.put(type, null);
                continue;
            }

            if (value.permissionChecker() != null) {
                result.put(type, new PermissionRule<>(parserService.decode(value.permissionChecker())));
                continue;
            }

            result.put(type, new PermissionRule<>(null));
        }

        return result;
    }

    private static <T> PermissionActionRule<T> toDomain(PermissionActionRuleDO data,
                                                        PredicateParserService parserService) {
        if (data == null) {
            return null;
        }

        if (data.permissionChecker() == null) {
            return new PermissionActionRule<>(null);
        }

        return new PermissionActionRule<>(parserService.decodeBi(data.permissionChecker()));
    }

    private UserPermissionData() {

    }
}
