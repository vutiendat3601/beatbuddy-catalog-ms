package vn.io.datvutech.beatbuddy.catalog.exception;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(
            String resourceType,
            String param,
            String paramValue

    ) {
        super(
                "%s not found with the given input data %s : '%s'"
                        .formatted(resourceType, param, paramValue)

        );
    }
}
