package vn.io.datvutech.beatbuddy.catalog.exception;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.method.ParameterValidationResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import jakarta.servlet.http.HttpServletRequest;
import vn.io.datvutech.beatbuddy.catalog.dto.ErrorResponseDto;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleGlobalException(
            Exception e,
            HttpServletRequest req

    ) {
        ErrorResponseDto errorRespDto = new ErrorResponseDto(
                req.getServletPath(),
                HttpStatus.INTERNAL_SERVER_ERROR,
                e.getMessage(),
                ZonedDateTime.now());
        return new ResponseEntity<>(errorRespDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleResourceNotFoundException(
            ResourceNotFoundException e,
            HttpServletRequest req

    ) {
        ErrorResponseDto errorRespDto = new ErrorResponseDto(
                req.getServletPath(),
                HttpStatus.NOT_FOUND,
                e.getMessage(),
                ZonedDateTime.now());
        return new ResponseEntity<>(errorRespDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    protected ResponseEntity<?> handleMethodArgumentNotValid(
            HandlerMethodValidationException e,
            HttpServletRequest req

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
