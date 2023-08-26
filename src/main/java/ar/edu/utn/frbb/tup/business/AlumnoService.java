package ar.edu.utn.frbb.tup.business;

import ar.edu.utn.frbb.tup.model.Alumno;
import ar.edu.utn.frbb.tup.model.EstadoAsignatura;
import ar.edu.utn.frbb.tup.model.Materia;
import ar.edu.utn.frbb.tup.model.dto.AlumnoDto;
import ar.edu.utn.frbb.tup.model.exception.AsignaturaInexistenteException;
import ar.edu.utn.frbb.tup.model.exception.CorrelatividadesNoAprobadasException;
import ar.edu.utn.frbb.tup.model.exception.EstadoIncorrectoException;
import ar.edu.utn.frbb.tup.persistence.exception.AlumnoNotFoundException;

import java.util.List;

public interface AlumnoService {

    // Crear, modificar, eliminar y buscar alumno ------------------------------

    Alumno crearAlumno(AlumnoDto alumno, List<Materia> materiasExistentes);

    Alumno modificarAlumno(long idAlumno, AlumnoDto alumno) throws AlumnoNotFoundException;

    Alumno buscarAlumno(long id) throws AlumnoNotFoundException;

    String borrarAlumno(long id) throws AlumnoNotFoundException;

    // ------------------------------------------------------------------------

    // aprobar, cursar, perder regularidad asignatura -------------------------

    void aprobarAsignatura(int materiaId, int nota, long dni) throws EstadoIncorrectoException,
            CorrelatividadesNoAprobadasException;

    void cursarAsignatura(long idAlumno, long idAsignatura);

    void perderRegularidadAsignatura(long idAlumno, long idAsignatura) throws EstadoIncorrectoException;

    // ------------------------------------------------------------------------

    // Otros métodos con relación a asignatura --------------------------------

    EstadoAsignatura buscarEstadoAsignatura(long idAlumno, long idAsignatura)
            throws AlumnoNotFoundException, AsignaturaInexistenteException;

    void actualizarAsignaturasAlumnos(Materia materia);

    // ------------------------------------------------------------------------

}
