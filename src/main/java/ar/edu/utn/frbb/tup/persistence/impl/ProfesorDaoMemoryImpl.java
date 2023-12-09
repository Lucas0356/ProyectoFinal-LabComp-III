package ar.edu.utn.frbb.tup.persistence.impl;

import ar.edu.utn.frbb.tup.model.Profesor;
import ar.edu.utn.frbb.tup.model.dto.ProfesorDto;
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
    public void saveProfesor(Profesor profesor) {

        // Asignamos el valor actual del contadorId
        profesor.setId(contadorId);

        // Incrementamos el contador para el próximo profesor
        contadorId++;

        // Guardamos al nuevo profesor
        repositorioProfesores.put(profesor.getId(), profesor);
    }

    @Override
    public Profesor findProfesor(long idProfesor) throws ProfesorNotFoundException {

        // Verificamos si el profesor existe en el repositorio
        verificarExistenciaProfesor(idProfesor);

        return repositorioProfesores.get(idProfesor);
    }

    @Override
    public Profesor updateProfesor(long idProfesor, ProfesorDto profesorModificado)
            throws ProfesorNotFoundException {

        // Verificamos si el profesor existe en el repositorio
        verificarExistenciaProfesor(idProfesor);

        // Obtenemos el profesor ya existente
        Profesor profesor = repositorioProfesores.get(idProfesor);

        // Obtenemos los nuevos valores de nombre, apellido y título del profesor
        String nuevoNombre = profesorModificado.getNombre();
        String nuevoApellido = profesorModificado.getApellido();
        String nuevoTitulo = profesorModificado.getTitulo();

        // Actualizamos los campos del profesor existente si es que no son nulos o vacíos
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
    public void deleteProfesor(long idProfesor) throws ProfesorNotFoundException {

        // Verificamos si el profesor existe en el repositorio
        verificarExistenciaProfesor(idProfesor);

        // Borramos al profesor
        repositorioProfesores.remove(idProfesor);
    }

    // ----------------------------------------------------------------

    // Métodos auxiliares ---------------------------------------------

    private void verificarExistenciaProfesor(long idProfesor) throws ProfesorNotFoundException {

        // Verificamos si el profesor existe en el repositorio
        if (!repositorioProfesores.containsKey(idProfesor)) {
            throw new ProfesorNotFoundException("No se encontró ningún profesor con el id " + idProfesor);
        }
    }

    // ----------------------------------------------------------------

}
