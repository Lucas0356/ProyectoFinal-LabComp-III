package ar.edu.utn.frbb.tup.persistence;

import ar.edu.utn.frbb.tup.model.Profesor;
import ar.edu.utn.frbb.tup.persistence.exception.ProfesorNotFoundException;

public interface ProfesorDao {

    // Métodos para operaciones CRUD de Profesor ----------------------

    Profesor saveProfesor(Profesor profesor);

    Profesor findProfesor(long idProfesor);

    Profesor updateProfesor(long id, Profesor profesor) throws ProfesorNotFoundException;

    void deleteProfesor(long idProfesor);

    // ----------------------------------------------------------------

}
