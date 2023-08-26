package ar.edu.utn.frbb.tup.persistence;

import ar.edu.utn.frbb.tup.model.Alumno;
import ar.edu.utn.frbb.tup.model.Asignatura;
import ar.edu.utn.frbb.tup.model.EstadoAsignatura;
import ar.edu.utn.frbb.tup.model.Materia;
import ar.edu.utn.frbb.tup.model.exception.AsignaturaInexistenteException;
import ar.edu.utn.frbb.tup.persistence.exception.AlumnoNotFoundException;
import ar.edu.utn.frbb.tup.persistence.exception.MateriaNotFoundException;

import java.util.List;

public interface AlumnoDao {

    // Métodos para operaciones CRUD de Alumnos ------------------------

    Alumno saveAlumno(Alumno alumno, List<Materia> materiasExistentes);

    Alumno findAlumno(long idAlumno) throws AlumnoNotFoundException;

    Alumno loadAlumno(Long dni);

    void deleteAlumno(long idAlumno);

    // ----------------------------------------------------------------

    // Métodos para operaciones relacionadas con asignaturas ----------

    void cursarAsignatura(long idAlumno, int idAsignatura) throws AlumnoNotFoundException;

    void aprobarAsignatura(long idAlumno, int idAsignatura, int nota);

    void perderRegularidadAsignatura(long idAlumno, int idAsignatura);

    EstadoAsignatura getEstadoAsignaturaPorId(long idAlumno, long idAsignatura) throws AlumnoNotFoundException,
            AsignaturaInexistenteException;

    public void agregarNuevaAsignaturaAAlumnos(Asignatura nuevaAsignatura);

    public void actualizarAlumno(Alumno alumno);

    // ----------------------------------------------------------------
}
