package de.derfrzocker.anime.calendar.collect.syoboi;

import com.eduardomcb.discord.webhook.WebhookClient;
import com.eduardomcb.discord.webhook.WebhookManager;
import com.eduardomcb.discord.webhook.models.Message;
import de.derfrzocker.anime.calendar.collect.syoboi.link.LinkService;
import de.derfrzocker.anime.calendar.impl.layer.config.BoundFilterConfig;
import de.derfrzocker.anime.calendar.impl.layer.config.SimpleIntegerLayerConfig;
import de.derfrzocker.anime.calendar.impl.layer.config.SimpleOffsetIntegerLayerConfig;
import de.derfrzocker.anime.calendar.impl.layer.config.StreamingTimeLayerConfig;
import de.derfrzocker.anime.calendar.impl.layer.filter.BoundLayerFilter;
import de.derfrzocker.anime.calendar.impl.layer.transformer.EpisodeLengthLayer;
import de.derfrzocker.anime.calendar.impl.layer.transformer.EpisodeNumberLayer;
import de.derfrzocker.anime.calendar.impl.layer.transformer.StreamingTimeLayer;
import de.derfrzocker.anime.calendar.server.core.api.anime.AnimeService;
import de.derfrzocker.anime.calendar.server.model.core.anime.AnimeId;
import de.derfrzocker.anime.calendar.server.model.domain.AnimeOptions;
import de.derfrzocker.anime.calendar.server.model.domain.Episode;
import de.derfrzocker.anime.calendar.server.model.domain.EpisodeBuilder;
import de.derfrzocker.anime.calendar.server.model.domain.Region;
import de.derfrzocker.anime.calendar.server.model.domain.anime.Anime;
import de.derfrzocker.anime.calendar.server.model.domain.event.AnimeAddLayerEvent;
import de.derfrzocker.anime.calendar.server.model.domain.layer.LayerFilterDataHolder;
import de.derfrzocker.anime.calendar.server.model.domain.layer.LayerHolder;
import de.derfrzocker.anime.calendar.server.model.domain.layer.LayerTransformerDataHolder;
import io.vertx.core.eventbus.EventBus;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@ApplicationScoped
public class SyoboiScheduleService {

    @Inject
    AnimeService animeService;
    @Inject
    AnimeDataDao animeDataDao;
    @RestClient
    SyoboiScheduleRestClient syoboiScheduleRestClient;
    @Inject
    LinkService linkService;
    @ConfigProperty(name = "discord.webhook.info.url")
    String webhookInfoUrl;
    @Inject
    EventBus eventBus;

    public void updateSchedule() {
        // TODO 2024-09-29: Magic value
        List<ScheduleData> scheduleData = parseScheduleResponse(getScheduleResponse(LocalDate.now(), 2));
        List<Data> combinedData = new ArrayList<>(filterData(scheduleData));

        combinedData.sort(Comparator.comparingInt(data -> data.scheduleData().episode()));

        for (Data data : combinedData) {
            AnimeId animeId = linkService.getAnimeId(data.tidData().tid());

            if (animeId == null) {
                System.out.println("Could not find anime for " + data);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                new WebhookManager().setListener(new WebhookClient.Callback() {
                    @Override
                    public void onSuccess(String response) {
                        System.out.println(response);
                    }

                    @Override
                    public void onFailure(int statusCode, String errorMessage) {
                        System.out.println(statusCode + " " + errorMessage);
                    }
                }).setChannelUrl(webhookInfoUrl).setMessage(new Message().setContent("\nCould not find anime for " + data + "\n")).exec();
                continue;
            }

            checkAnime(animeId, data);
        }
    }

    private void checkAnime(AnimeId animeId, Data data) {
        Anime anime = animeService.getAnime(animeId);
        Episode episode = getEpisode(anime, data);

        checkBaseStreamTime(anime, episode, data);
        checkEpisode(anime, episode, data);
        checkLength(anime, episode, data);
    }

    private void checkLength(Anime anime, Episode episode, Data data) {
        long duration = Duration.between(data.scheduleData().startTime(), data.scheduleData().endTime()).toMinutes();
        if (episode.episodeLength() == duration) {
            return;
        }

        LayerFilterDataHolder<BoundFilterConfig> layerFilterDataHolder = new LayerFilterDataHolder<>(BoundLayerFilter.INSTANCE, new BoundFilterConfig(data.scheduleData().episode() - 1, -1));
        LayerTransformerDataHolder<SimpleIntegerLayerConfig> layerTransformerDataHolder = new LayerTransformerDataHolder<>(EpisodeLengthLayer.INSTANCE, new SimpleIntegerLayerConfig((int) duration));
        LayerHolder layerHolder = new LayerHolder(List.of(layerFilterDataHolder), layerTransformerDataHolder);

        eventBus.publish("anime-add-layer", new AnimeAddLayerEvent(anime.animeId(), layerHolder));
    }

    private void checkEpisode(Anime anime, Episode episode, Data data) {
        if (episode.episodeNumber() != null && !episode.episodeNumber().equals(String.valueOf(data.scheduleData().episode()))) {
            return;
        }

        LayerFilterDataHolder<BoundFilterConfig> layerFilterDataHolder = new LayerFilterDataHolder<>(BoundLayerFilter.INSTANCE, new BoundFilterConfig(data.scheduleData().episode() - 1, -1));
        LayerTransformerDataHolder<SimpleOffsetIntegerLayerConfig> layerTransformerDataHolder = new LayerTransformerDataHolder<>(EpisodeNumberLayer.INSTANCE, new SimpleOffsetIntegerLayerConfig(data.scheduleData.episode(), data.scheduleData().episode() - 1));
        LayerHolder layerHolder = new LayerHolder(List.of(layerFilterDataHolder), layerTransformerDataHolder);

        eventBus.publish("anime-add-layer", new AnimeAddLayerEvent(anime.animeId(), layerHolder));
    }

    private void checkBaseStreamTime(Anime anime, Episode episode, Data data) {
        if (isCorrectTime(episode, data)) {
            return;
        }

        LayerFilterDataHolder<BoundFilterConfig> layerFilterDataHolder = new LayerFilterDataHolder<>(BoundLayerFilter.INSTANCE, new BoundFilterConfig(data.scheduleData().episode() - 1, -1));
        LayerTransformerDataHolder<StreamingTimeLayerConfig> layerTransformerDataHolder = new LayerTransformerDataHolder<>(StreamingTimeLayer.INSTANCE, new StreamingTimeLayerConfig(data.scheduleData().startTime(), Period.ofDays(7), data.scheduleData().episode() - 1, "org"));
        LayerHolder layerHolder = new LayerHolder(List.of(layerFilterDataHolder), layerTransformerDataHolder);

        eventBus.publish("anime-add-layer", new AnimeAddLayerEvent(anime.animeId(), layerHolder));
    }

    private boolean isCorrectTime(Episode episode, Data data) {
        if (episode.streamingTime() == null) {
            return false;
        }
        return Math.abs(episode.streamingTime().getEpochSecond() - data.scheduleData().startTime().getEpochSecond()) < 60;
    }

    private Episode getEpisode(Anime anime, Data data) {
        EpisodeBuilder episodeBuilder = EpisodeBuilder.anEpisode(data.scheduleData().episode() - 1);
        AnimeOptions animeOptions = new AnimeOptions(Region.DE_DE, false, "org");
        for (LayerHolder layerHolder : anime.transformerData().reversed()) {
            if (layerHolder.shouldSkip(anime, animeOptions, episodeBuilder)) {
                continue;
            }
            layerHolder.layerDataHolder().transform(anime, animeOptions, episodeBuilder);
        }

        return episodeBuilder.build();
    }

    private List<Data> filterData(List<ScheduleData> scheduleData) {
        return scheduleData
                .stream()
                .map(data -> new Data(data, animeDataDao.getData(data.tid())))
                .filter(data -> data.tidData() != null)
                .filter(data -> data.tidData().channel().equals(data.scheduleData().channel()))
                .filter(data -> {
                    if (data.tidData().end() == null) {
                        return true;
                    }

                    if (data.tidData().end().isBefore(YearMonth.from(data.scheduleData().startTime().atZone(ZoneId.of(ZoneId.SHORT_IDS.get("JST")))))) {
                        return false;
                    }

                    return true;
                })
                .toList();
    }

    private List<ScheduleData> parseScheduleResponse(ScheduleResponse scheduleResponse) {
        return scheduleResponse.Programs()
                .values()
                .stream()
                .filter(data -> data.Count() != null)
                .map(data -> new ScheduleData(new TID(data.TID()), new Channel(data.ChName()), Integer.parseInt(data.Count()), Instant.ofEpochSecond(Long.parseLong(data.StTime())), Instant.ofEpochSecond(Long.parseLong(data.EdTime()))))
                .toList();
    }

    private ScheduleResponse getScheduleResponse(LocalDate start, int days) {
        ScheduleResponse scheduleResponse = syoboiScheduleRestClient.getSchedule("ProgramByDate", start.toString(), days);

        try {
            // Sleep to not trigger api rate limite
            Thread.sleep(Duration.ofSeconds(2));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return scheduleResponse;
    }

    private record Data(ScheduleData scheduleData, TIDData tidData) {}
}
