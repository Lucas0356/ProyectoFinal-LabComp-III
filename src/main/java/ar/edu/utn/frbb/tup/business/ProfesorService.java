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

    Profesor buscarProfesor(String id) throws ProfesorNotFoundException;

    Profesor modificarProfesor(String id, ProfesorDto profesor) throws ProfesorNotFoundException;

    String borrarProfesor(String id) throws ProfesorNotFoundException, MateriaNotFoundException;

    List<Materia> obtenerMateriasDictadasProfesor(String id) throws ProfesorNotFoundException;

    // ------------------------------------------------------------------------

}
