package de.derfrzocker.anime.calendar.server.mongodb.mapper.domain;

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
import org.bson.Document;

public final class UserPermissionDomain {

    public static UserPermissionDO toData(UserPermission domain, PredicateParserService parserService) {
        UserPermissionDO data = new UserPermissionDO();

        data.id = domain.id();
        data.user = toData(domain.user(), parserService);
        data.calendar = toData(domain.calendar(), parserService);
        data.anime = toData(domain.anime(), parserService);
        data.calendarAnimeLink = toData(domain.calendarAnimeLink(), parserService);
        data.apply(domain);

        return data;
    }

    private static <T> ObjectPermissionDO toData(ObjectPermission<T> domain, PredicateParserService parserService) {
        if (domain == null) {
            return null;
        }

        return new ObjectPermissionDO(toData(domain.permissionRules(), parserService),
                                      toData(domain.actionRules(), parserService));
    }

    private static <T> Map<String, PermissionRuleDO> toData(Map<PermissionType, PermissionRule<T>> domain,
                                                            PredicateParserService parserService) {

        if (domain == null) {
            return null;
        }

        Map<String, PermissionRuleDO> result = new HashMap<>();
        for (Map.Entry<PermissionType, PermissionRule<T>> entry : domain.entrySet()) {
            PermissionType type = entry.getKey();
            Document document = new Document();
            document.putAll(parserService.encode(entry.getValue().permissionChecker()));
            result.put(type.toString(), new PermissionRuleDO(document));
        }

        return result;
    }


    private static <T> PermissionActionRuleDO toData(PermissionActionRule<T> domain,
                                                     PredicateParserService parserService) {

        if (domain == null) {
            return null;
        }

        if (domain.permissionChecker() == null) {
            return new PermissionActionRuleDO(null);
        }

        Document document = new Document();
        document.putAll(parserService.encodeBi(domain.permissionChecker()));

        return new PermissionActionRuleDO(document);
    }

    private UserPermissionDomain() {

    }
}
