package ar.edu.utn.frbb.tup.business;

import ar.edu.utn.frbb.tup.model.Alumno;
import ar.edu.utn.frbb.tup.model.Asignatura;
import ar.edu.utn.frbb.tup.model.dto.AsignaturaDto;
import ar.edu.utn.frbb.tup.model.exception.AsignaturaInexistenteException;
import ar.edu.utn.frbb.tup.model.exception.CorrelatividadesNoAprobadasException;
import ar.edu.utn.frbb.tup.persistence.exception.NotaIncorrectaException;
import ar.edu.utn.frbb.tup.persistence.exception.AlumnoNotFoundException;

public interface AsignaturaService {

    // Modificar Asignatura ----------------------------------------------------

    Asignatura modAsignatura(int asignaturaID, Alumno alumno);

    void perderRegularidadAsignatura(long idAlumno, long idAsignatura);

    void cursarAsignatura(long idAlumno, long idAsignatura, AsignaturaDto asignaturaDto) throws AsignaturaInexistenteException, AlumnoNotFoundException;

    void aprobarAsignatura(long idAlumno, int nota, long asignaturaDto) throws AsignaturaInexistenteException, AlumnoNotFoundException, NotaIncorrectaException, CorrelatividadesNoAprobadasException;

    // -------------------------------------------------------------------------

    // Obtener asignatura ------------------------------------------------------

    Asignatura buscarAsignatura(long idAsignatura, long idAlumno)
            throws AsignaturaInexistenteException, AlumnoNotFoundException;

    // -------------------------------------------------------------------------
}
