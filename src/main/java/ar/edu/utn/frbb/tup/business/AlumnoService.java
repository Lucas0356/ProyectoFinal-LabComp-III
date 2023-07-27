package ar.edu.utn.frbb.tup.business;

import ar.edu.utn.frbb.tup.model.Alumno;
import ar.edu.utn.frbb.tup.model.dto.AlumnoDto;
import ar.edu.utn.frbb.tup.model.exception.CorrelatividadesNoAprobadasException;
import ar.edu.utn.frbb.tup.model.exception.EstadoIncorrectoException;

public interface AlumnoService {

    // Crear, modificar, eliminar y buscar alumno ------------------------------

    Alumno crearAlumno(AlumnoDto alumno);

    void eliminarAlumno(long idAlumno);

    Alumno modificarAlumno(long idAlumno, AlumnoDto alumno);

    Alumno buscarAlumno(int id);

    // ------------------------------------------------------------------------

    // aprobar, cursar y perder regularidad asignatura ------------------------

    void aprobarAsignatura(int materiaId, int nota, long dni) throws EstadoIncorrectoException,
            CorrelatividadesNoAprobadasException;

    void cursarAsignatura(long idAlumno, long idAsignatura);

    void perderRegularidadAsignatura(long idAlumno, long idAsignatura) throws EstadoIncorrectoException;

    // ------------------------------------------------------------------------

}
