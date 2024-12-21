package de.derfrzocker.anime.calendar.server.impl.myanimelist.client;

import java.util.List;

public record MyAnimeListUserListResponse(List<Data> data) {

    public record Data(Node node) {}
    public record Node(int id) {}
}
