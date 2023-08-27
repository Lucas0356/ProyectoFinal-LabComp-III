package ar.edu.utn.frbb.tup.business;

import ar.edu.utn.frbb.tup.model.Materia;
import ar.edu.utn.frbb.tup.model.dto.MateriaDto;
import ar.edu.utn.frbb.tup.persistence.exception.MateriaNotFoundException;
import ar.edu.utn.frbb.tup.persistence.exception.ProfesorNotFoundException;

import java.util.List;

public interface MateriaService {

    // Crear, modificar y eliminar materia ------------------------------------

    Materia crearMateria(MateriaDto inputData) throws ProfesorNotFoundException;

    // ------------------------------------------------------------------------

    // Obtener una y todas las materias ---------------------------------------

    List<Materia> obtenerTodasLasMaterias();

    Materia buscarMateria(int idMateria) throws MateriaNotFoundException;

    // -------------------------------------------------------------------------
}
