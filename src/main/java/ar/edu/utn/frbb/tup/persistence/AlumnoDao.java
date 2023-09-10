package ar.edu.utn.frbb.tup.persistence;

import ar.edu.utn.frbb.tup.model.Alumno;
import ar.edu.utn.frbb.tup.model.Asignatura;
import ar.edu.utn.frbb.tup.model.EstadoAsignatura;
import ar.edu.utn.frbb.tup.model.Materia;
import ar.edu.utn.frbb.tup.model.dto.AlumnoDto;
import ar.edu.utn.frbb.tup.model.dto.AsignaturaDto;
import ar.edu.utn.frbb.tup.model.exception.AsignaturaInexistenteException;
import ar.edu.utn.frbb.tup.model.exception.CorrelatividadesNoAprobadasException;
import ar.edu.utn.frbb.tup.persistence.exception.NotaIncorrectaException;
import ar.edu.utn.frbb.tup.persistence.exception.AlumnoNotFoundException;

import java.util.List;

public interface AlumnoDao {

    // Métodos para operaciones CRUD de Alumnos ------------------------

    Alumno saveAlumno(Alumno alumno, List<Materia> materiasExistentes);

    Alumno findAlumno(long idAlumno) throws AlumnoNotFoundException;

    Alumno updateAlumno(long idAlumno, AlumnoDto alumnoModificado) throws AlumnoNotFoundException;

    void deleteAlumno(long idAlumno) throws AlumnoNotFoundException;

    // ----------------------------------------------------------------

    // Métodos para operaciones relacionadas con asignaturas ----------

    void cursarAsignatura(long idAlumno, long idAsignatura)
            throws AlumnoNotFoundException, AsignaturaInexistenteException;

    void aprobarAsignatura(long idAlumno, int nota, long idAsignatura) throws AlumnoNotFoundException, AsignaturaInexistenteException, NotaIncorrectaException, CorrelatividadesNoAprobadasException;

    void perderRegularidadAsignatura(long idAlumno, long idAsignatura) throws AsignaturaInexistenteException, AlumnoNotFoundException;

    EstadoAsignatura getEstadoAsignaturaPorId(long idAlumno, long idAsignatura) throws AlumnoNotFoundException,
            AsignaturaInexistenteException;

    Asignatura getAsignaturaPorId(long idAlumno, long idAsignatura) throws AlumnoNotFoundException,
            AsignaturaInexistenteException;

    void actualizarAsignaturasAlumnos(Materia nuevaMateria);

    // ----------------------------------------------------------------
}
