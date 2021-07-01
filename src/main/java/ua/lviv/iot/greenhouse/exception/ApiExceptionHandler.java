package ua.lviv.iot.greenhouse.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = {NoDataFoundException.class})
    public ResponseEntity<Object> handleNoDataFoundException(NoDataFoundException e) {
        // Create payload containing exception details
        HttpStatus notFound = HttpStatus.NOT_FOUND;

        ApiException apiException = new ApiException(
                e.getMessage(),
                notFound,
                ZonedDateTime.now()
        );

        // Return response entity
        return new ResponseEntity<>(apiException, notFound);
    }

    @ExceptionHandler(value = {WrongDateFormatException.class})
    public ResponseEntity<Object> handleWrongDateFormatException(WrongDateFormatException e) {
        // Create payload containing exception details
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;

        ApiException apiException = new ApiException(
                e.getMessage(),
                badRequest,
                ZonedDateTime.now()
        );

        // Return response entity
        return new ResponseEntity<>(apiException, badRequest);
    }
}
