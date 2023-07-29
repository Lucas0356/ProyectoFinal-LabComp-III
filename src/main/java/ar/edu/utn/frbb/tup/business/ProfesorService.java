package ar.edu.utn.frbb.tup.business;

import ar.edu.utn.frbb.tup.model.Profesor;
import ar.edu.utn.frbb.tup.model.dto.ProfesorDto;
import ar.edu.utn.frbb.tup.persistence.exception.ProfesorNotFoundException;

public interface ProfesorService{

    // Métodos ----------------------------------------------------------------

    Profesor crearProfesor(ProfesorDto profesor);

    Profesor buscarProfesor(String id) throws ProfesorNotFoundException;

    Profesor modificarProfesor(String id, ProfesorDto profesor) throws ProfesorNotFoundException;

    // ------------------------------------------------------------------------

}
