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

    void perderRegularidadAsignatura(long idAlumno, long idAsignatura) throws AsignaturaInexistenteException, AlumnoNotFoundException;

    void cursarAsignatura(long idAlumno, long idAsignatura) throws AsignaturaInexistenteException, AlumnoNotFoundException;

    void aprobarAsignatura(long idAlumno, int nota, long asignaturaDto) throws AsignaturaInexistenteException, AlumnoNotFoundException, NotaIncorrectaException, CorrelatividadesNoAprobadasException;

    // -------------------------------------------------------------------------

    // Obtener asignatura ------------------------------------------------------

    Asignatura buscarAsignatura(long idAsignatura, long idAlumno)
            throws AsignaturaInexistenteException, AlumnoNotFoundException;

    // -------------------------------------------------------------------------
}
