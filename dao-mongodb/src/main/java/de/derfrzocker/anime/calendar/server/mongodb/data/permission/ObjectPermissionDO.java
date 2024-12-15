package de.derfrzocker.anime.calendar.server.mongodb.data.permission;

import java.util.Map;

public record ObjectPermissionDO(Map<String, PermissionRuleDO> permissionRules, PermissionActionRuleDO actionRules) {

}
