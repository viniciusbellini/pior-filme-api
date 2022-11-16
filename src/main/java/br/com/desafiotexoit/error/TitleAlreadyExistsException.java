package br.com.desafiotexoit.error;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class TitleAlreadyExistsException extends DataIntegrityViolationException {
    public TitleAlreadyExistsException(String message) {
        super(message);
    }
}
