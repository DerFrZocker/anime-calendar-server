package de.derfrzocker.anime.calendar.server.integration.syoboi.dao;

import static de.derfrzocker.anime.calendar.server.integration.syoboi.mapper.ChannelDataMapper.toData;
import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.Channel;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.ChannelId;
import de.derfrzocker.anime.calendar.server.integration.syoboi.mapper.ChannelDataMapper;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import java.util.Optional;
import java.util.stream.Stream;

@Dependent
public class ChannelMongoDBDaoImpl implements ChannelDao {

    @Inject
    ChannelMongoDBRepository repository;

    @Override
    public Stream<Channel> getAll(RequestContext context) {
        return this.repository.findAll().stream().map(ChannelDataMapper::toDomain);
    }

    @Override
    public Optional<Channel> getById(ChannelId id, RequestContext context) {
        return this.repository.findByIdOptional(id).map(ChannelDataMapper::toDomain);
    }

    @Override
    public void create(Channel channel, RequestContext context) {
        this.repository.persist(toData(channel));
    }

    @Override
    public void update(Channel channel, RequestContext context) {
        this.repository.update(toData(channel));
    }

    @Override
    public void delete(Channel channel, RequestContext context) {
        this.repository.deleteById(channel.id());
    }
}
