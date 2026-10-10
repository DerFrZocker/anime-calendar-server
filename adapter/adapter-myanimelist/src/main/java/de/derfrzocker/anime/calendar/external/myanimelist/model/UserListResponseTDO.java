package de.derfrzocker.anime.calendar.external.myanimelist.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Objects;

public record UserListResponseTDO(@JsonProperty("data") List<UserListDataTDO> data) {

    public UserListResponseTDO {
        Objects.requireNonNull(data);
    }
}
