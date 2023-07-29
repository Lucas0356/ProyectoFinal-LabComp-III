package ar.edu.utn.frbb.tup.persistence.impl;

import ar.edu.utn.frbb.tup.model.Profesor;
import ar.edu.utn.frbb.tup.persistence.ProfesorDao;
import ar.edu.utn.frbb.tup.persistence.exception.ProfesorNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ProfesorDaoMemoryImpl implements ProfesorDao {

    // Atributos -------------------------------------------------------

    private static long contadorId = 1;

    private static Map<Long, Profesor> repositorioProfesores = new HashMap<>();

    // -----------------------------------------------------------------


    // Métodos para operaciones CRUD de Profesor ----------------------

    @Override
    public Profesor saveProfesor(Profesor profesor) {
        profesor.setId(contadorId++);
        repositorioProfesores.put(profesor.getId(), profesor);
        return profesor;
    }

    @Override
    public Profesor findProfesor(long idProfesor) {
        return repositorioProfesores.get(idProfesor);
    }

    @Override
    public Profesor updateProfesor(long id, Profesor profesorModificado) throws ProfesorNotFoundException {
        // Verificamos si el profesor existe en el repositorio
        if (!repositorioProfesores.containsKey(id)) {
            throw new ProfesorNotFoundException("No se encontró ningún profesor con el ID proporcionado.");
        }

        // Obtenemos el profesor ya existente
        Profesor profesor = repositorioProfesores.get(id);

        // Obtenemos los nuevos valores de nombre, apellido y título del profesor
        String nuevoNombre = profesorModificado.getNombre();
        String nuevoApellido = profesorModificado.getApellido();
        String nuevoTitulo = profesorModificado.getTitulo();

        // Actualizamos los campos del profesor existente si es que no son nulos y vacíos
        if (nuevoNombre != null && !nuevoNombre.isEmpty()) {
            profesor.setNombre(nuevoNombre);
        }

        if (nuevoApellido != null && !nuevoApellido.isEmpty()) {
            profesor.setApellido(nuevoApellido);
        }

        if (nuevoTitulo != null && !nuevoTitulo.isEmpty()) {
            profesor.setTitulo(nuevoTitulo);
        }

        return profesor;
    }

    @Override
    public void deleteProfesor(long idProfesor) {
    }

    // ----------------------------------------------------------------
}
