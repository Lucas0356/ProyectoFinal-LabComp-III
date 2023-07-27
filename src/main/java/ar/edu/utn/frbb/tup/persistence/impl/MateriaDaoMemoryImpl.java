package ar.edu.utn.frbb.tup.persistence.impl;

import ar.edu.utn.frbb.tup.model.Materia;
import ar.edu.utn.frbb.tup.persistence.MateriaDao;
import ar.edu.utn.frbb.tup.persistence.exception.MateriaNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class MateriaDaoMemoryImpl implements MateriaDao {

    // Atributos -------------------------------------------------------

    private static final Map<Integer, Materia> repositorioMateria = new HashMap<>();

    // ----------------------------------------------------------------

    // Métodos para operaciones CRUD de Materias ------------------------

    @Override
    public Materia findMateria(int idMateria) throws MateriaNotFoundException {
        for (Materia materia:
             repositorioMateria.values()) {
            if (idMateria == materia.getMateriaId()) {
                return materia;
            }
        }
        throw new MateriaNotFoundException("No se encontró la materia con id " + idMateria);
    }

    @Override
    public Materia saveMateria(Materia materia) {
        Random random = new Random();
        materia.setMateriaId(random.nextInt());
        repositorioMateria.put(materia.getMateriaId(), materia);
        return materia;
    }

    @Override
    public void deleteMateria(int idMateria) {
    }

    // ----------------------------------------------------------------

    // Métodos para operaciones relacionadas con correlatividades -----

    @Override
    public void agregarCorrelatividad(int idMateria, int idCorrelatividad) {
    }

    // ----------------------------------------------------------------
}
