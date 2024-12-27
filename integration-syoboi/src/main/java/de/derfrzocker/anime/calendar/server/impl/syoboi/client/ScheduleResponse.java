package de.derfrzocker.anime.calendar.server.impl.syoboi.client;

import java.util.Map;

public record ScheduleResponse(Map<String, Data> Programs) {

    public record Data(String PID, String TID, String ChName, String Count, String StTime, String EdTime) {

    }
}
