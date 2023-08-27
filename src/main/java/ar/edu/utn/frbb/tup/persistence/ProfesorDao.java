package ar.edu.utn.frbb.tup.persistence;

import ar.edu.utn.frbb.tup.model.Profesor;
import ar.edu.utn.frbb.tup.model.dto.ProfesorDto;
import ar.edu.utn.frbb.tup.persistence.exception.ProfesorNotFoundException;

public interface ProfesorDao {

    // Métodos para operaciones CRUD de Profesor ----------------------

    void saveProfesor(Profesor profesor);

    Profesor findProfesor(long idProfesor) throws ProfesorNotFoundException;

    Profesor updateProfesor(long id, ProfesorDto profesor) throws ProfesorNotFoundException;

    void deleteProfesor(long idProfesor) throws ProfesorNotFoundException;

    // ----------------------------------------------------------------

}
