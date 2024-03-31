package vn.io.vutiendat3601.beatbuddy.catalog.exception;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.method.MethodValidationException;
import org.springframework.validation.method.ParameterValidationResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.server.ServerWebExchange;

import lombok.extern.slf4j.Slf4j;
import vn.io.vutiendat3601.beatbuddy.catalog.dto.ErrorResponseDto;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleGlobalException(
            Exception e,
            ServerWebExchange exchange

    ) {
        log.error("Internal server error: {}", e.getMessage());
        String apiPath = "" + exchange.getRequest().getPath();
        ErrorResponseDto errorRespDto = new ErrorResponseDto(
                apiPath,
                HttpStatus.INTERNAL_SERVER_ERROR,
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                ZonedDateTime.now());
        return new ResponseEntity<>(errorRespDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleResourceNotFoundException(
            ResourceNotFoundException e,
            ServerWebExchange exchange

    ) {
        log.error(e.getMessage());
        String apiPath = "" + exchange.getRequest().getPath();
        ErrorResponseDto errorRespDto = new ErrorResponseDto(
                apiPath,
                HttpStatus.NOT_FOUND,
                e.getMessage(),
                ZonedDateTime.now()

        );
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(errorRespDto);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException e

    ) {
        final Map<String, List<String>> validationErrors = new HashMap<>();
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        fieldErrors.forEach(fe -> {
            String fieldName = fe.getField();
            List<String> messages = Optional.ofNullable(validationErrors.get(fieldName))
                    .orElseGet(() -> {
                        List<String> newMessages = new LinkedList<>();
                        validationErrors.put(fieldName, newMessages);
                        return newMessages;
                    });
            messages.add(fe.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(validationErrors);
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<?> handleHandlerMethodArgumentNotValid(
            HandlerMethodValidationException e

    ) {
        final Map<String, String[]> validationErrors = new HashMap<>();
        List<ParameterValidationResult> valiationResults = e.getValueResults();
        valiationResults.forEach(vr -> {
            MethodParameter param = vr.getMethodParameter();
            String messages[] = vr.getResolvableErrors().stream().map(re -> re.getDefaultMessage())
                    .toArray(String[]::new);
            validationErrors.put(param.getParameterName(), messages);
        });

        return ResponseEntity.badRequest().body(validationErrors);
    }

    @ExceptionHandler(MethodValidationException.class)
    public ResponseEntity<?> handleMethodArgumentNotValid(
            MethodValidationException e

    ) {
        final Map<String, String[]> validationErrors = new HashMap<>();
        List<ParameterValidationResult> valiationResults = e.getValueResults();
        valiationResults.forEach(vr -> {
            MethodParameter param = vr.getMethodParameter();
            String messages[] = vr.getResolvableErrors().stream().map(re -> re.getDefaultMessage())
                    .toArray(String[]::new);
            validationErrors.put(param.getParameterName(), messages);
        });

        return ResponseEntity.badRequest().body(validationErrors);
    }
}
