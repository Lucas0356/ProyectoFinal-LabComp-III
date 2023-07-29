package ar.edu.utn.frbb.tup.persistence.impl;

import ar.edu.utn.frbb.tup.business.exceptions.IdInvalidoException;
import ar.edu.utn.frbb.tup.business.exceptions.NumeroInvalidoException;
import ar.edu.utn.frbb.tup.model.Materia;
import ar.edu.utn.frbb.tup.model.Profesor;
import ar.edu.utn.frbb.tup.persistence.MateriaDao;
import ar.edu.utn.frbb.tup.persistence.exception.MateriaNotFoundException;
import ar.edu.utn.frbb.tup.persistence.exception.ProfesorNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class MateriaDaoMemoryImpl implements MateriaDao {

    // Atributos -------------------------------------------------------

    private static int contadorId = 1;

    private static final Map<Integer, Materia> repositorioMateria = new HashMap<>();

    // ----------------------------------------------------------------

    // Métodos para operaciones CRUD de Materias ------------------------

    @Override
    public Materia findMateria(int idMateria) throws MateriaNotFoundException {
        // Buscar la materia por el ID
        Materia materia =  repositorioMateria.get(idMateria);

        // Si no encuentra la materia con el ID:
        if (materia == null) {
            throw new MateriaNotFoundException("No se encontró la materia con el id " + idMateria);
        }

        return materia;
    }

    @Override
    public Materia saveMateria(Materia materia) {
        materia.setMateriaId(contadorId++);
        repositorioMateria.put(materia.getMateriaId(), materia);
        Profesor profesor = materia.getProfesor();
        profesor.agregarMateriaDictada(materia);
        return materia;
    }

    @Override
    public void deleteMateria(int idMateria) throws MateriaNotFoundException {
        // Verificamos si la materia existe en el repositorio
        if (!repositorioMateria.containsKey(idMateria)) {
            throw new MateriaNotFoundException("No se encontró la materia con el id " + idMateria);
        }

        // Borramos la materia
        repositorioMateria.remove(idMateria);
    }

    // ----------------------------------------------------------------

    // Métodos para operaciones relacionadas con correlatividades -----

    @Override
    public void agregarCorrelatividad(int idMateria, int idCorrelatividad) {
    }

    // ----------------------------------------------------------------

}
