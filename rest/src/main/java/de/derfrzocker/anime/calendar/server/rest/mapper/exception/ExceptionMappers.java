package de.derfrzocker.anime.calendar.server.rest.mapper.exception;

import de.derfrzocker.anime.calendar.server.model.domain.exception.ResourceNotFoundException;
import de.derfrzocker.anime.calendar.server.validation.exception.InvalidIdException;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.reactive.RestResponse;
import org.jboss.resteasy.reactive.server.ServerExceptionMapper;

public class ExceptionMappers {

    @ServerExceptionMapper
    public RestResponse<ExceptionTO> mapException(InvalidIdException exception) {
        return build(Response.Status.BAD_REQUEST, exception.getMessage());
    }

    @ServerExceptionMapper
    public RestResponse<ExceptionTO> mapException(ResourceNotFoundException exception) {
        return build(Response.Status.NOT_FOUND, exception.getMessage());
    }

    private RestResponse<ExceptionTO> build(Response.StatusType status, String message) {
        return RestResponse.status(status, new ExceptionTO(message, status.getStatusCode()));
    }

    public record ExceptionTO(String message, int code) {

    }
}
