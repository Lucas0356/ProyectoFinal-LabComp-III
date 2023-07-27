package ar.edu.utn.frbb.tup.persistence.impl;

import ar.edu.utn.frbb.tup.model.Alumno;
import ar.edu.utn.frbb.tup.model.Profesor;
import ar.edu.utn.frbb.tup.persistence.ProfesorDao;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ProfesorDaoMemoryImpl implements ProfesorDao {

    // Atributos -------------------------------------------------------

    private static Map<Long, Profesor> repositorioProfesores = new HashMap<>();

    // -----------------------------------------------------------------


    // Métodos para operaciones CRUD de Profesor ----------------------

    @Override
    public Profesor saveProfesor(Profesor profesor) {
        return null;
    }

    @Override
    public Profesor findProfesor(long idProfesor) {
        return null;
    }

    @Override
    public void deleteProfesor(long idProfesor) {
    }

    // ----------------------------------------------------------------

    // Métodos varios -------------------------------------------------

    @Override
    public Profesor get(long id) {
        return new Profesor("Luciano", "Salotto", "Lic. Ciencias Computación");
    }

    // ----------------------------------------------------------------
}
