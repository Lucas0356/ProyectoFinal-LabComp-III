package ar.edu.utn.frbb.tup.business;

import ar.edu.utn.frbb.tup.model.Materia;
import ar.edu.utn.frbb.tup.model.Profesor;
import ar.edu.utn.frbb.tup.model.dto.ProfesorDto;
import ar.edu.utn.frbb.tup.persistence.exception.MateriaNotFoundException;
import ar.edu.utn.frbb.tup.persistence.exception.ProfesorNotFoundException;

import java.util.List;

public interface ProfesorService{

    // Métodos para operaciones CRUD de Profesor ------------------------------

    Profesor crearProfesor(ProfesorDto profesor);

    Profesor buscarProfesor(long id) throws ProfesorNotFoundException;

    Profesor modificarProfesor(long id, ProfesorDto profesor) throws ProfesorNotFoundException;

    String borrarProfesor(long id) throws ProfesorNotFoundException, MateriaNotFoundException;

    // ------------------------------------------------------------------------

    // Métodos relacionados con las materias dictadas -------------------------

    List<Materia> obtenerMateriasDictadasProfesor(long id) throws ProfesorNotFoundException;

    // ------------------------------------------------------------------------

}
