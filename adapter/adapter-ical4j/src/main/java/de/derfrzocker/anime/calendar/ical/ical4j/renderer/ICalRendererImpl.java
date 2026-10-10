package de.derfrzocker.anime.calendar.ical.ical4j.renderer;

import de.derfrzocker.anime.calendar.ical.out.ICalRenderer;
import java.time.Duration;
import java.time.Instant;
import java.util.function.Consumer;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.Description;
import net.fortuna.ical4j.model.property.DtStart;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.Summary;
import net.fortuna.ical4j.model.property.Uid;
import net.fortuna.ical4j.model.property.immutable.ImmutableCalScale;
import net.fortuna.ical4j.model.property.immutable.ImmutableVersion;

public class ICalRendererImpl implements ICalRenderer {

    private final Calendar calendar = new Calendar();

    private ICalRendererImpl() {
        calendar.add(ImmutableVersion.VERSION_2_0);
        calendar.add(ImmutableCalScale.GREGORIAN);
    }

    public static ICalRenderer create() {
        return new ICalRendererImpl();
    }

    @Override
    public ICalRenderer prodId(String prodId) {
        calendar.add(new ProdId.Factory().createProperty(prodId));

        return this;
    }

    @Override
    public ICalRenderer uid(String uid) {
        calendar.add(new Uid.Factory().createProperty(uid));

        return this;
    }

    @Override
    public ICalRenderer event(Consumer<ICalEventRenderer> event) {
        ICalEventRendererImpl iCalEventRenderer = new ICalEventRendererImpl();

        event.accept(iCalEventRenderer);

        calendar.add(iCalEventRenderer.vEvent());

        return this;
    }

    @Override
    public String render() {
        return calendar.toString();
    }

    private static class ICalEventRendererImpl implements ICalEventRenderer {

        private final VEvent vEvent = new VEvent();

        @Override
        public ICalEventRenderer summary(String summary) {
            vEvent.add(new Summary(summary));

            return this;
        }

        @Override
        public ICalEventRenderer description(String description) {
            vEvent.add(new Description(description));

            return this;
        }

        @Override
        public ICalEventRenderer start(Instant start) {
            vEvent.add(new DtStart<>(start));

            return this;
        }

        @Override
        public ICalEventRenderer duration(Duration duration) {
            vEvent.add(new net.fortuna.ical4j.model.property.Duration(duration));

            return this;
        }

        @Override
        public ICalEventRenderer uid(String uid) {
            vEvent.add(new Uid.Factory().createProperty(uid));

            return this;
        }

        VEvent vEvent() {
            return vEvent;
        }
    }
}
