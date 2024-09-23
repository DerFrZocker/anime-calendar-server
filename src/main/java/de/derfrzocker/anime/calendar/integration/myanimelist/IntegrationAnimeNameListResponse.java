package de.derfrzocker.anime.calendar.integration.myanimelist;

import java.util.List;

public record IntegrationAnimeNameListResponse(List<Data> data) {

    public record Data(Node node) {}
    public record Node(int id, String title) {}
}
