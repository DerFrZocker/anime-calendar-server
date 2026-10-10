package de.derfrzocker.anime.calendar.ical.ical4j.adapter;

import de.derfrzocker.anime.calendar.ical.ical4j.renderer.ICalRendererImpl;
import de.derfrzocker.anime.calendar.ical.out.ForCreatingICalRenderer;
import de.derfrzocker.anime.calendar.ical.out.ICalRenderer;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
class ICalRendererService implements ForCreatingICalRenderer {

    @Override
    public ICalRenderer createRenderer() {
        return ICalRendererImpl.create();
    }
}
