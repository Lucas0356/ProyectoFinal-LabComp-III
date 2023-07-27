package ar.edu.utn.frbb.tup.persistence;

import ar.edu.utn.frbb.tup.model.Materia;
import ar.edu.utn.frbb.tup.persistence.exception.MateriaNotFoundException;

public interface MateriaDao {

    // Métodos para operaciones CRUD de Materias ------------------------

    Materia saveMateria(Materia materia);

    Materia findMateria(int idMateria) throws MateriaNotFoundException;

    void deleteMateria(int idMateria);

    // ----------------------------------------------------------------

    // Métodos para operaciones relacionadas con correlatividades -----

    void agregarCorrelatividad(int idMateria, int idCorrelatividad);

    // ----------------------------------------------------------------

}
