package de.derfrzocker.anime.calendar.server.rest.mapper.exception;

import de.derfrzocker.anime.calendar.server.model.domain.exception.BadRequestException;
import de.derfrzocker.anime.calendar.server.model.domain.exception.ResourceNotFoundException;
import de.derfrzocker.anime.calendar.server.model.domain.exception.UnauthenticatedException;
import de.derfrzocker.anime.calendar.server.model.domain.exception.UnauthorizedException;
import de.derfrzocker.anime.calendar.server.validation.exception.InvalidIdException;
import de.derfrzocker.anime.calendar.server.validation.exception.InvalidKeyException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.reactive.RestResponse;
import org.jboss.resteasy.reactive.server.ServerExceptionMapper;

public class ExceptionMappers {

    @ServerExceptionMapper
    public RestResponse<ExceptionTO> mapException(InvalidIdException exception) {
        return build(Response.Status.BAD_REQUEST, exception.getMessage());
    }

    @ServerExceptionMapper
    public RestResponse<ExceptionTO> mapException(InvalidKeyException exception) {
        return build(Response.Status.BAD_REQUEST, exception.getMessage());
    }

    @ServerExceptionMapper
    public RestResponse<ExceptionTO> mapException(ResourceNotFoundException exception) {
        return build(Response.Status.NOT_FOUND, exception.getMessage());
    }

    @ServerExceptionMapper
    public RestResponse<ExceptionTO> mapException(UnauthenticatedException exception) {
        return build(Response.Status.UNAUTHORIZED, exception.getMessage());
    }

    @ServerExceptionMapper
    public RestResponse<ExceptionTO> mapException(BadRequestException exception) {
        return build(Response.Status.BAD_REQUEST, exception.getMessage());
    }

    @ServerExceptionMapper
    public RestResponse<ExceptionTO> mapException(UnauthorizedException exception) {
        return build(Response.Status.FORBIDDEN, exception.getMessage());
    }

    private RestResponse<ExceptionTO> build(Response.StatusType status, String message) {
        return RestResponse.ResponseBuilder.create(status, new ExceptionTO(message, status.getStatusCode()))
                                           .type(MediaType.APPLICATION_JSON_TYPE)
                                           .build();
    }

    public record ExceptionTO(String message, int code) {

    }
}
