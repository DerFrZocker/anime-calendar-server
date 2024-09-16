package de.derfrzocker.anime.calendar.integration.myanimelist;

import java.util.List;

public record IntegrationUserListResponse(List<Data> data) {

    public record Data(Node node) {}
    public record Node(int id) {}
}
