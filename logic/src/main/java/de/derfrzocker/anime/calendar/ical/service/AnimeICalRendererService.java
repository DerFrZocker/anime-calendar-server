package de.derfrzocker.anime.calendar.ical.service;

import de.derfrzocker.anime.calendar.anime.EpisodeBuildOption;
import de.derfrzocker.anime.calendar.ical.out.ForCreatingICalRenderer;
import de.derfrzocker.anime.calendar.ical.out.ICalRenderer;
import de.derfrzocker.anime.calendar.ical.renderer.AnimeICalRenderer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class AnimeICalRendererService {

    private final ForCreatingICalRenderer iCalRendererProvider;

    @Inject
    AnimeICalRendererService(ForCreatingICalRenderer iCalRendererProvider) {
        this.iCalRendererProvider = iCalRendererProvider;
    }

    public AnimeICalRenderer createRenderer(EpisodeBuildOption buildOption) {

        ICalRenderer renderer = iCalRendererProvider.createRenderer();

        return AnimeICalRenderer.with(buildOption, renderer);
    }
}
