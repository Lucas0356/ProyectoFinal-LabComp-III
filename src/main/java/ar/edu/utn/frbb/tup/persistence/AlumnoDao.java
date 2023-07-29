package ar.edu.utn.frbb.tup.persistence;

import ar.edu.utn.frbb.tup.model.Alumno;
import ar.edu.utn.frbb.tup.persistence.exception.AlumnoNotFoundException;

public interface AlumnoDao {

    // Métodos para operaciones CRUD de Alumnos ------------------------

    Alumno saveAlumno(Alumno alumno);

    Alumno findAlumno(long idAlumno) throws AlumnoNotFoundException;

    Alumno loadAlumno(Long dni);

    void deleteAlumno(long idAlumno);

    // ----------------------------------------------------------------

    // Métodos para operaciones relacionadas con asignaturas ----------

    void cursarAsignatura(long idAlumno, int idAsignatura);

    void aprobarAsignatura(long idAlumno, int idAsignatura, int nota);

    void perderRegularidadAsignatura(long idAlumno, int idAsignatura);

    // ----------------------------------------------------------------
}
