package ar.edu.utn.frbb.tup.business;

import ar.edu.utn.frbb.tup.model.Materia;
import ar.edu.utn.frbb.tup.model.dto.MateriaDto;
import ar.edu.utn.frbb.tup.persistence.exception.MateriaNotFoundException;

import java.util.List;

public interface MateriaService {

    // Crear, modificar y eliminar materia ------------------------------------

    Materia crearMateria(MateriaDto inputData) throws IllegalArgumentException;

    // ------------------------------------------------------------------------

    // Obtener una y todas las materias ---------------------------------------

    List<Materia> getAllMaterias();

    Materia getMateriaById(int idMateria) throws MateriaNotFoundException;

    // -------------------------------------------------------------------------
}
