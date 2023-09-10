package ar.edu.utn.frbb.tup.business;

import ar.edu.utn.frbb.tup.model.Alumno;
import ar.edu.utn.frbb.tup.model.EstadoAsignatura;
import ar.edu.utn.frbb.tup.model.Materia;
import ar.edu.utn.frbb.tup.model.dto.AlumnoDto;
import ar.edu.utn.frbb.tup.model.dto.AsignaturaDto;
import ar.edu.utn.frbb.tup.model.exception.AsignaturaInexistenteException;
import ar.edu.utn.frbb.tup.model.exception.CorrelatividadesNoAprobadasException;
import ar.edu.utn.frbb.tup.model.exception.EstadoIncorrectoException;
import ar.edu.utn.frbb.tup.persistence.exception.NotaIncorrectaException;
import ar.edu.utn.frbb.tup.persistence.exception.AlumnoNotFoundException;
import ar.edu.utn.frbb.tup.persistence.exception.MateriaNotFoundException;

import java.util.List;

public interface AlumnoService {

    // Métodos para operaciones CRUD de Alumno --------------------------------

    Alumno crearAlumno(AlumnoDto alumno, List<Materia> materiasExistentes);

    Alumno buscarAlumno(long id) throws AlumnoNotFoundException;

    Alumno modificarAlumno(long idAlumno, AlumnoDto alumno) throws AlumnoNotFoundException;

    String borrarAlumno(long id) throws AlumnoNotFoundException;

    // ------------------------------------------------------------------------

    // Métodos relacionados con asignatura ------------------------------

    EstadoAsignatura buscarEstadoAsignatura(long idAlumno, long idAsignatura)
            throws AlumnoNotFoundException, AsignaturaInexistenteException;

    void actualizarAsignaturasAlumnos(Materia materia);

    void modificarAsignatura(long idAlumno, long idAsignatura, AsignaturaDto asignaturaDto)
            throws AsignaturaInexistenteException, AlumnoNotFoundException, EstadoIncorrectoException, CorrelatividadesNoAprobadasException, NotaIncorrectaException;

    // ------------------------------------------------------------------------

}
