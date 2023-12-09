package ar.edu.utn.frbb.tup.persistence;

import ar.edu.utn.frbb.tup.model.Materia;
import ar.edu.utn.frbb.tup.persistence.exception.MateriaNotFoundException;

import java.util.List;
import java.util.Map;

public interface MateriaDao {

    // Métodos para operaciones CRUD de Materias ------------------------

    Materia saveMateria(Materia materia);

    Materia findMateria(int idMateria) throws MateriaNotFoundException;

    void deleteMateria(int idMateria) throws MateriaNotFoundException;

    // ----------------------------------------------------------------

    // Método auxiliar para obtener todas las materias  ---------------

    public List<Materia> obtenerTodasLasMaterias();

    // ----------------------------------------------------------------

}
