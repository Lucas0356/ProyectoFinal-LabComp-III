package ar.edu.utn.frbb.tup.controller.handler;

import ar.edu.utn.frbb.tup.business.exceptions.TituloInvalidoException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import ar.edu.utn.frbb.tup.business.exceptions.ApellidoInvalidoException;
import ar.edu.utn.frbb.tup.business.exceptions.NombreInvalidoException;
import ar.edu.utn.frbb.tup.persistence.exception.MateriaNotFoundException;

@ControllerAdvice
public class UtnResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    // Manejar excepción de materia no encontrada
    @ExceptionHandler(value = {MateriaNotFoundException.class})
    protected ResponseEntity<Object> handleMateriaNotFound(MateriaNotFoundException ex, WebRequest request) {
        String exceptionMessage = ex.getMessage();
        CustomApiError error = new CustomApiError();
        error.setErrorMessage(exceptionMessage);
        return handleExceptionInternal(ex, error, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    // Manejar excepciones de argumentos no válidos (por ejemplo, IllegalArgumentException e IllegalStateException)
    @ExceptionHandler(value = {IllegalArgumentException.class, IllegalStateException.class})
    protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {
        String exceptionMessage = ex.getMessage();
        CustomApiError error = new CustomApiError();
        error.setErrorCode(1234);
        error.setErrorMessage(exceptionMessage);
        return handleExceptionInternal(ex, error, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    // Manejar excepción de nombre no válido
    @ExceptionHandler(value = {NombreInvalidoException.class})
    protected ResponseEntity<Object> handleNombreInvalido(NombreInvalidoException ex, WebRequest request) {
        CustomApiError error = new CustomApiError();
        error.setErrorMessage(ex.getMessage());
        return handleExceptionInternal(ex, error, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    // Manejar excepción de apellido no válido
    @ExceptionHandler(value = {ApellidoInvalidoException.class})
    protected ResponseEntity<Object> handleApellidoInvalido(ApellidoInvalidoException ex, WebRequest request) {
        CustomApiError error = new CustomApiError();
        error.setErrorMessage(ex.getMessage());
        return handleExceptionInternal(ex, error, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    // Manejar excepción de título no válido
    @ExceptionHandler(value = {TituloInvalidoException.class})
    protected ResponseEntity<Object> handleTituloInvalido(TituloInvalidoException ex, WebRequest request) {
        CustomApiError error = new CustomApiError();
        error.setErrorMessage(ex.getMessage());
        return handleExceptionInternal(ex, error, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, @Nullable Object body, HttpHeaders headers,
                                                             HttpStatus status, WebRequest request) {
        if (body == null) {
            CustomApiError error = new CustomApiError();
            error.setErrorMessage(ex.getMessage());
            body = error;
        }

        return new ResponseEntity<>(body, headers, status);
    }
}
