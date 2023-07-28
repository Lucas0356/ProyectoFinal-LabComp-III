package ar.edu.utn.frbb.tup.persistence;

import ar.edu.utn.frbb.tup.model.Profesor;

public interface ProfesorDao {

    // Métodos para operaciones CRUD de Profesor ----------------------

    Profesor saveProfesor(Profesor profesor);

    Profesor findProfesor(long idProfesor);

    void deleteProfesor(long idProfesor);

    // ----------------------------------------------------------------

}
