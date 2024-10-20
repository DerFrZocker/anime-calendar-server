package de.derfrzocker.anime.calendar.collect.anidb;

import de.derfrzocker.anime.calendar.collect.anidb.mongodb.AnimeNamesListDO;
import de.derfrzocker.anime.calendar.collect.anidb.mongodb.AnimeNamesListRepository;
import de.derfrzocker.anime.calendar.collect.anidb.season.SeasonService;
import de.derfrzocker.anime.calendar.collect.anidb.season.udp.SeasonData;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.zip.GZIPInputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.text.similarity.LevenshteinDistance;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

@ApplicationScoped
public class AniDBNameResolverServices {

    @RestClient
    AniDBRestDao aniDBRestDao;
    @ConfigProperty(name = "anidb-name-resolver.distance-threshold")
    int distanceThreshold;
    LevenshteinDistance levenshteinDistance;
    @ConfigProperty(name = "anidb-name-resolver.language")
    String language;
    @Inject
    AnimeNamesListRepository animeNamesListRepository;
    @Inject
    SeasonService seasonService;
    @ConfigProperty(name = "anidb-name-resolver.anime-names-valid-duration")
    Duration animeNamesValidDuration;


    @PostConstruct
    void init() {
        levenshteinDistance = new LevenshteinDistance(distanceThreshold);
    }

    public AnimeNameSearchResult convertName(String name) {
        List<AnimeNames> allAnimeNames = readAnimeNames();
        List<AnimeNames> animeNames = filterAnimeNames(allAnimeNames, seasonService.getSeasonData());

        Pair<Set<AnimeNames>, Integer> best = getBest(animeNames, name);

        if (!isValid(best)) {
            best = getBest(allAnimeNames, name);
        }

        if (!isValid(best)) {
            return null;
        }

        if (best.getLeft().size() != 1) {
            // throw new RuntimeException("Cannot determinate anime for name " + name + " got following results " + best.getLeft());
            // System.out.println("Ambiges values " + best.getRight() + " " + name + " " + best.getLeft().size() + " " + best.getLeft());
            return null;
        }

        AnimeNames result = best.getLeft().iterator().next();

        AnimeTitle animeTitle = findBestTitle(result);

        return new AnimeNameSearchResult(result.externalAnimeId(), animeTitle.title(), best.getRight());
    }

    private boolean isValid(Pair<Set<AnimeNames>, Integer> best) {
        if (best.getRight() > distanceThreshold) {
            return false;
        }

        if (best.getLeft().isEmpty()) {
            return false;
        }

        return true;
    }

    private AnimeTitle findBestTitle(AnimeNames animeNames) {
        for (AnimeTitle animeTitle : animeNames.animeTitles()) {
            if (animeTitle.titleType() == TitleType.MAIN && animeTitle.language().equals(language)) {
                return animeTitle;
            }
        }

        throw new RuntimeException("No main language set for " + animeNames);
    }

    private List<AnimeNames> filterAnimeNames(List<AnimeNames> animeNames, List<SeasonData> seasonData) {
        List<AnimeNames> filteredAnimeNames = new ArrayList<>();

        seasonData.forEach(season -> animeNames
                .stream()
                .filter(names -> names.externalAnimeId().equals(season.externalAnimeId()))
                .forEach(filteredAnimeNames::add)
        );

        return filteredAnimeNames;
    }

    private List<AnimeNames> readAnimeNames() {
        AnimeNamesListDO animeNamesListDO = animeNamesListRepository.findAll().firstResult();
        if (animeNamesListDO == null || animeNamesListDO.validUntil.isBefore(Instant.now())) {
            if (animeNamesListDO != null) {
                animeNamesListRepository.delete(animeNamesListDO);
            }
            animeNamesListDO = new AnimeNamesListDO();
            animeNamesListDO.validUntil = Instant.now().plus(animeNamesValidDuration);
            animeNamesListDO.animeNames = readAnimeNames(aniDBRestDao.getAnimeNames());

            animeNamesListRepository.persist(animeNamesListDO);
        }

        return animeNamesListDO.animeNames;
    }

    private List<AnimeNames> readAnimeNames(File file) {
        List<AnimeNames> animeNames = new ArrayList<>();
        try {
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            try (GZIPInputStream inputStream = new GZIPInputStream(new FileInputStream(file))) {
                Document document = documentBuilder.parse(inputStream);

                Element root = (Element) document.getElementsByTagName("animetitles").item(0);
                NodeList animes = root.getElementsByTagName("anime");
                for (int i = 0; i < animes.getLength(); i++) {
                    Node childNode = animes.item(i);
                    if (childNode.getNodeType() != Node.ELEMENT_NODE) {
                        continue;
                    }

                    Element element = (Element) childNode;
                    ExternalAnimeId externalAnimeId = new ExternalAnimeId(element.getAttribute("aid"));
                    NodeList titles = element.getElementsByTagName("title");

                    List<AnimeTitle> animeTitles = new ArrayList<>();
                    for (int j = 0; j < titles.getLength(); j++) {
                        Node titleNode = titles.item(j);
                        if (titleNode.getNodeType() != Node.ELEMENT_NODE) {
                            continue;
                        }
                        Element titleElement = (Element) titleNode;
                        animeTitles.add(new AnimeTitle(titleElement.getAttribute("xml:lang"), TitleType.valueOf(titleElement.getAttribute("type").toUpperCase(Locale.ROOT)), titleElement.getTextContent()));
                    }

                    animeNames.add(new AnimeNames(externalAnimeId, animeTitles));
                }
            }
        } catch (ParserConfigurationException | IOException | SAXException e) {
            throw new RuntimeException(e);
        }

        file.delete();

        return animeNames;
    }

    private Pair<Set<AnimeNames>, Integer> getBest(List<AnimeNames> names, String name) {
        Set<AnimeNames> best = new HashSet<>();
        int currentBest = Integer.MAX_VALUE;

        for (AnimeNames animeName : names) {
            for (AnimeTitle animeTitle : animeName.animeTitles()) {
                // TODO 2024-09-30: Magic value
                if (!animeTitle.language().equals("ja") && !animeTitle.language().equals("en") && !animeTitle.title().equals(language)) {
                    continue;
                }

                if (!animeTitle.titleType().equals(TitleType.MAIN) && !animeTitle.titleType().equals(TitleType.OFFICIAL)) {
                    continue;
                }

                int distance = levenshteinDistance.apply(animeTitle.title(), name);
                if (distance == -1) {
                    continue;
                }
                if (distance < currentBest) {
                    currentBest = distance;
                    best.clear();
                    best.add(animeName);
                } else if (distance == currentBest) {
                    best.add(animeName);
                }
            }
        }

        if (currentBest > (name.length() / 2)) {
            return Pair.of(Set.of(), Integer.MAX_VALUE);
        }

        return Pair.of(best, currentBest);
    }
}
