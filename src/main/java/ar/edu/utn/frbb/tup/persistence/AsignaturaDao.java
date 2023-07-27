package ar.edu.utn.frbb.tup.persistence;

import ar.edu.utn.frbb.tup.model.Asignatura;

public interface AsignaturaDao {

    // Métodos para operaciones CRUD de Asignatura ---------------------

    Asignatura saveAsignatura(Asignatura asignatura);

    Asignatura findAsignatura(long idAsignatura);

    void deleteAsignatura(long idAsignatura);

    // -----------------------------------------------------------------
}
