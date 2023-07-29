package ar.edu.utn.frbb.tup.business;

import ar.edu.utn.frbb.tup.model.Alumno;
import ar.edu.utn.frbb.tup.model.dto.AlumnoDto;
import ar.edu.utn.frbb.tup.model.dto.ProfesorDto;
import ar.edu.utn.frbb.tup.model.exception.CorrelatividadesNoAprobadasException;
import ar.edu.utn.frbb.tup.model.exception.EstadoIncorrectoException;
import ar.edu.utn.frbb.tup.persistence.exception.AlumnoNotFoundException;

public interface AlumnoService {

    // Crear, modificar, eliminar y buscar alumno ------------------------------

    Alumno crearAlumno(AlumnoDto alumno);

    void eliminarAlumno(long idAlumno);

    Alumno modificarAlumno(String idAlumno, ProfesorDto alumno);

    Alumno buscarAlumno(String id) throws AlumnoNotFoundException, AlumnoNotFoundException;

    String borrarAlumno(String id);

    // ------------------------------------------------------------------------

    // aprobar, cursar y perder regularidad asignatura ------------------------

    void aprobarAsignatura(int materiaId, int nota, long dni) throws EstadoIncorrectoException,
            CorrelatividadesNoAprobadasException;

    void cursarAsignatura(long idAlumno, long idAsignatura);

    void perderRegularidadAsignatura(long idAlumno, long idAsignatura) throws EstadoIncorrectoException;

    // ------------------------------------------------------------------------

}
