package br.com.desafiotexoit.handler;

import br.com.desafiotexoit.error.ApplicationExceptions;
import br.com.desafiotexoit.error.ApplicationExceptionsDetails;
import br.com.desafiotexoit.error.TitleAlreadyExistsException;
import br.com.desafiotexoit.error.ValidationErrorDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler(ApplicationExceptions.class)
    public ResponseEntity<?> handleApplicationNotFoundException(ApplicationExceptions appException) {
        ApplicationExceptionsDetails applicationExceptionsDetails = ApplicationExceptionsDetails.Builder.newBuilder()
                .timestamp(new Date().getTime())
                .status(HttpStatus.NOT_FOUND.value())
                .title("Resource not found")
                .detail(appException.getMessage())
                .developerMessage(appException.getClass().getName())
                .build();
        return new ResponseEntity<>(applicationExceptionsDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException methodArgumentNotValidException) {

        List<FieldError> fieldErrors = methodArgumentNotValidException.getBindingResult().getFieldErrors();
        String fields = fieldErrors.stream().map(FieldError::getField).collect(Collectors.joining(","));
        String fieldMessages = fieldErrors.stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(","));
        ValidationErrorDetails validationErrorDetails = ValidationErrorDetails.Builder
                .newBuilder()
                .timestamp(new Date().getTime())
                .status(HttpStatus.BAD_REQUEST.value())
                .title("Field validation error")
                .detail(methodArgumentNotValidException.getMessage())
                .developerMessage(methodArgumentNotValidException.getClass().getName())
                .field(fields)
                .fieldMessage(fieldMessages)
                .build();
        return new ResponseEntity<>(validationErrorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TitleAlreadyExistsException.class)
    public ResponseEntity<?> handleDataIntegrityViolationException(TitleAlreadyExistsException titleAlreadyExistsException) {

        ApplicationExceptionsDetails applicationExceptionsDetails = ApplicationExceptionsDetails.Builder
                .newBuilder()
                .timestamp(new Date().getTime())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .title("Field validation error")
                .detail(titleAlreadyExistsException.getMessage())
                .developerMessage(titleAlreadyExistsException.getClass().getName())
                .build();
        return new ResponseEntity<>(applicationExceptionsDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
