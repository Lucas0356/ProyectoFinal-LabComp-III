package ar.edu.utn.frbb.tup.controller.handler;

import ar.edu.utn.frbb.tup.business.exceptions.*;
import ar.edu.utn.frbb.tup.model.exception.CorrelatividadesNoAprobadasException;
import ar.edu.utn.frbb.tup.model.exception.EstadoIncorrectoException;
import ar.edu.utn.frbb.tup.persistence.exception.NotaIncorrectaException;
import ar.edu.utn.frbb.tup.persistence.exception.AlumnoNotFoundException;
import ar.edu.utn.frbb.tup.persistence.exception.ProfesorNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import ar.edu.utn.frbb.tup.persistence.exception.MateriaNotFoundException;

@ControllerAdvice
public class UtnResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    // Manejar excepción para entidades no encontradas (Materia, Profesor, etc.)
    @ExceptionHandler(value = {MateriaNotFoundException.class, ProfesorNotFoundException.class,
    AlumnoNotFoundException.class})
    protected ResponseEntity<Object> handleEntityNotFoundException(Exception ex, WebRequest request) {
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

    // Manejar excepciones de argumentos no válidos (NombreInvalidoException, ApellidoInvalidoException,
    // TituloInvalidoException, IdInvalidoException, etc)
    @ExceptionHandler(value = {NombreInvalidoException.class, ApellidoInvalidoException.class,
            TituloInvalidoException.class, IdInvalidoException.class, NumeroInvalidoException.class,
            AnioInvalidoException.class, CuatrimestreInvalidoException.class, DniInvalidoException.class,
            NotaIncorrectaException.class, EstadoIncorrectoException.class, CorrelatividadesNoAprobadasException.class})
    protected ResponseEntity<Object> handleInvalidArguments(Exception ex, WebRequest request) {
        String exceptionMessage = ex.getMessage();
        CustomApiError error = new CustomApiError();
        error.setErrorMessage(exceptionMessage);
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
