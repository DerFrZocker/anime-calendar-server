package de.derfrzocker.anime.calendar.server.integration.syoboi.dao;

import de.derfrzocker.anime.calendar.server.integration.syoboi.api.Channel;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.ProvidedTIDData;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.TID;
import de.derfrzocker.anime.calendar.server.integration.syoboi.data.ProvidedTIDDataTDO;
import de.derfrzocker.anime.calendar.server.integration.syoboi.data.TitleMediumResponseTDO;
import de.derfrzocker.anime.calendar.server.integration.syoboi.mapper.ProvidedTIDDataDataMapper;
import de.derfrzocker.anime.calendar.server.model.domain.RequestContext;
import jakarta.enterprise.context.Dependent;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@Dependent
public class TIDDataProviderRestDaoImpl implements TIDDataProviderDao {

    @RestClient
    TIDDataProviderRestClient restClient;

    @Override
    public Optional<ProvidedTIDData> provideById(TID id, RequestContext context) {
        return this.restClient.getTitleMedium(id.raw())
                              .map(TitleMediumResponseTDO::Titles)
                              .map(titles -> titles.get(id.raw()))
                              .map(this::toDomain);
    }

    private ProvidedTIDData toDomain(ProvidedTIDDataTDO data) {
        TID tid = new TID(data.TID());
        String title = data.Title();
        YearMonth firstStart = YearMonth.of(Integer.parseInt(data.FirstYear()), Integer.parseInt(data.FirstMonth()));
        YearMonth firstEnd = null;

        if (data.FirstEndYear() != null) {
            firstEnd = YearMonth.of(Integer.parseInt(data.FirstEndYear()), Integer.parseInt(data.FirstEndMonth()));
        }

        List<Channel> firstChannels = parseChannels(data.FirstCh());

        return ProvidedTIDDataDataMapper.toDomain(tid, title, firstStart, firstEnd, firstChannels);
    }

    private List<Channel> parseChannels(String channel) {
        if (channel == null || channel.isEmpty()) {
            return new ArrayList<>();
        }

        List<Channel> result = new ArrayList<>();

        if (channel.contains("、")) {
            String[] split = channel.split("、");

            for (String s : split) {
                result.add(new Channel(s));
            }
        } else if (channel.contains("・")) {
            String[] split = channel.split("・");

            for (String s : split) {
                result.add(new Channel(s));
            }
        } else if (channel.contains(",")) {
            String[] split = channel.split(",");

            for (String s : split) {
                result.add(new Channel(s));
            }
        } else {
            result.add(new Channel(channel));
        }

        return result;
    }
}
