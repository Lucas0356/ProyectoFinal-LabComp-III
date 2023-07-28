package ar.edu.utn.frbb.tup.business;

import ar.edu.utn.frbb.tup.model.Profesor;
import ar.edu.utn.frbb.tup.model.dto.ProfesorDto;

public interface ProfesorService{

    // Métodos ----------------------------------------------------------------

    Profesor crearProfesor(ProfesorDto profesor) throws IllegalArgumentException;

    Profesor buscarProfesor(long id);

    // ------------------------------------------------------------------------

}
