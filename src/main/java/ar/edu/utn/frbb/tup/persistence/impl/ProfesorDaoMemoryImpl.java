package ar.edu.utn.frbb.tup.persistence.impl;

import ar.edu.utn.frbb.tup.model.Profesor;
import ar.edu.utn.frbb.tup.persistence.ProfesorDao;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ProfesorDaoMemoryImpl implements ProfesorDao {

    // Atributos -------------------------------------------------------

    private static long contadorId = 0;

    private static Map<Long, Profesor> repositorioProfesores = new HashMap<>();

    // -----------------------------------------------------------------


    // Métodos para operaciones CRUD de Profesor ----------------------

    @Override
    public Profesor saveProfesor(Profesor profesor) {
        profesor.setId(contadorId++);
        repositorioProfesores.put(profesor.getId(), profesor);
        return profesor;
        // return repositorioProfesores.put(profesor.getId(), profesor);
    }

    @Override
    public Profesor findProfesor(long idProfesor) {
        return repositorioProfesores.get(idProfesor);
    }

    @Override
    public void deleteProfesor(long idProfesor) {
    }

    // ----------------------------------------------------------------
}
