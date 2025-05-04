package de.derfrzocker.anime.calendar.server.integration.impl.service;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.integration.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.core.integration.IntegrationId;
import de.derfrzocker.anime.calendar.core.integration.IntegrationUserId;
import de.derfrzocker.anime.calendar.server.integration.dao.IntegrationUserDao;
import de.derfrzocker.anime.calendar.server.integration.service.IntegrationUserService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Instance;
import jakarta.enterprise.inject.literal.NamedLiteral;
import jakarta.inject.Inject;
import java.util.Set;

@ApplicationScoped
public class IntegrationUserServiceImpl implements IntegrationUserService {

    @Inject
    Instance<IntegrationUserDao> daoInstance;

    @Override
    public Set<IntegrationAnimeId> getAnimeIds(IntegrationId integrationId,
                                               IntegrationUserId user,
                                               RequestContext context) {

        return selectDao(integrationId).getAnimeIds(user, context);
    }

    private IntegrationUserDao selectDao(IntegrationId integrationId) {
        return this.daoInstance.select(NamedLiteral.of(integrationId.raw() + IntegrationUserDao.NAME_SUFFIX)).get();
    }
}
