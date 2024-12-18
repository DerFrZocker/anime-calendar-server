package de.derfrzocker.anime.calendar.server.impl.name.anidb.handler;

import de.derfrzocker.anime.calendar.server.core.api.name.AnimeNameHolderService;
import de.derfrzocker.anime.calendar.server.impl.name.anidb.client.AniDBRestClient;
import de.derfrzocker.anime.calendar.server.model.core.integration.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.server.model.core.integration.IntegrationId;
import de.derfrzocker.anime.calendar.server.model.domain.RequestContext;
import de.derfrzocker.anime.calendar.server.model.domain.name.AnimeName;
import de.derfrzocker.anime.calendar.server.model.domain.name.AnimeNameHolder;
import de.derfrzocker.anime.calendar.server.model.domain.name.AnimeNameHolderCreateData;
import de.derfrzocker.anime.calendar.server.model.domain.name.AnimeNameHolderUpdateData;
import de.derfrzocker.anime.calendar.server.model.domain.name.NameLanguage;
import de.derfrzocker.anime.calendar.server.model.domain.name.NameType;
import de.derfrzocker.anime.calendar.server.model.domain.util.FixedChange;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.infrastructure.Infrastructure;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.context.control.ActivateRequestContext;
import jakarta.inject.Inject;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.zip.GZIPInputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

@RequestScoped
public class AniDBNameUpdateRequestHandler {

    private static final IntegrationId ANIDB = new IntegrationId("anidb");

    private static final Logger LOG = Logger.getLogger(AniDBNameUpdateRequestHandler.class);

    @RestClient
    AniDBRestClient restClient;
    @Inject
    AnimeNameHolderService service;

    public Uni<Void> createOrUpdate(RequestContext context) {
        List<ParsedAnimeNameHolder> nameHolders = readAll();

        LOG.info("Creating or updating '%d' anime names".formatted(nameHolders.size()));

        // TODO 2024-12-17: Read a bit more into context propagation, can this be done better?
        return Multi.createFrom()
                    .iterable(nameHolders)
                    .emitOn(Infrastructure.getDefaultExecutor())
                    .invoke(holder -> createOrUpdate(holder, context))
                    .collect()
                    .asList()
                    .onFailure()
                    .invoke(d -> {
                        LOG.error("AniDB names creating or updating failed.", d);
                    })
                    .invoke(() -> {
                        LOG.info("AniDB names created or updated successfully.");
                    })
                    .replaceWithVoid();
    }

    // Need to have this annotation otherwise multi won't work, because of missing active context :|
    @ActivateRequestContext
    void createOrUpdate(ParsedAnimeNameHolder read, RequestContext context) {
        Optional<AnimeNameHolder> current = this.service.getById(ANIDB, read.integrationAnimeId(), context);

        if (current.isPresent()) {
            update(read, current.get(), context);
        } else {
            create(read, context);
        }
    }

    private void create(ParsedAnimeNameHolder read, RequestContext context) {
        this.service.createWithData(ANIDB,
                                    read.integrationAnimeId(),
                                    new AnimeNameHolderCreateData(read.names()),
                                    context);
    }

    private void update(ParsedAnimeNameHolder read, AnimeNameHolder current, RequestContext context) {
        if (read.names().size() != current.names().size()) {
            this.service.updateWithData(ANIDB,
                                        read.integrationAnimeId(),
                                        new AnimeNameHolderUpdateData(FixedChange.of(read.names())),
                                        context);
        }

        boolean same = true;
        for (AnimeName name : read.names()) {
            if (!current.names().contains(name)) {
                same = false;
                break;
            }
        }

        if (same) {
            return;
        }

        this.service.updateWithData(ANIDB,
                                    read.integrationAnimeId(),
                                    new AnimeNameHolderUpdateData(FixedChange.of(read.names())),
                                    context);
    }

    private List<ParsedAnimeNameHolder> readAll() {
        File file = this.restClient.getAnimeNames();
        Document document = parse(file);

        if (!file.delete()) {
            throw new RuntimeException("Could not delete file '%s'.".formatted(file.getAbsolutePath()));
        }

        List<ParsedAnimeNameHolder> holders = new ArrayList<>();
        Element root = (Element) document.getElementsByTagName("animetitles").item(0);
        NodeList animes = root.getElementsByTagName("anime");

        for (int i = 0; i < animes.getLength(); i++) {
            Node anime = animes.item(i);
            if (anime.getNodeType() != Node.ELEMENT_NODE) {
                // TODO 2024-12-17: Better exception
                throw new RuntimeException("Unexpected node type: '%s'.".formatted(anime.getNodeType()));
            }

            holders.add(parse((Element) anime));
        }

        return holders;
    }

    private ParsedAnimeNameHolder parse(Element anime) {
        List<AnimeName> animeNames = new ArrayList<>();
        IntegrationAnimeId integrationAnimeId = new IntegrationAnimeId(anime.getAttribute("aid"));
        NodeList names = anime.getElementsByTagName("title");

        for (int i = 0; i < names.getLength(); i++) {
            Node name = names.item(i);
            if (name.getNodeType() != Node.ELEMENT_NODE) {
                // TODO 2024-12-17: Better exception
                throw new RuntimeException("Unexpected node type: '%s'.".formatted(anime.getNodeType()));
            }

            animeNames.add(parseName((Element) name));
        }

        return new ParsedAnimeNameHolder(integrationAnimeId, animeNames);
    }

    private AnimeName parseName(Element name) {
        return new AnimeName(new NameType(name.getAttribute("type")),
                             new NameLanguage(name.getAttribute("xml:lang")),
                             name.getTextContent());
    }

    private Document parse(File file) {
        try {
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            try (GZIPInputStream inputStream = new GZIPInputStream(new FileInputStream(file))) {
                return documentBuilder.parse(inputStream);
            }
        } catch (ParserConfigurationException | IOException | SAXException e) {
            // TODO 2024-12-17: Better exception
            throw new RuntimeException(e);
        }
    }

    private record ParsedAnimeNameHolder(IntegrationAnimeId integrationAnimeId, List<AnimeName> names) {

    }
}
