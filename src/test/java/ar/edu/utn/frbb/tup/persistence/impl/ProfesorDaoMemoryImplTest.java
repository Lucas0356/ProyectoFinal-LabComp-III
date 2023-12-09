package ar.edu.utn.frbb.tup.persistence.impl;

import ar.edu.utn.frbb.tup.model.Profesor;
import ar.edu.utn.frbb.tup.model.dto.ProfesorDto;
import ar.edu.utn.frbb.tup.persistence.exception.ProfesorNotFoundException;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ProfesorDaoMemoryImplTest {

    @Test
    void saveProfesorTest() {
        // Crear una instancia de la implementación ProfesorDaoMemoryImpl
        ProfesorDaoMemoryImpl profesorDao = new ProfesorDaoMemoryImpl();

        // Crear un profesor ficticio
        Profesor profesor = new Profesor();
        profesor.setNombre("Eduardo");
        profesor.setApellido("Ruiz");
        profesor.setTitulo("Ingeniero en Sistemas");

        // Guardar al profesor
        profesorDao.saveProfesor(profesor);

        // Verifica que el ID del profesor no sea nulo
        assertNotNull(profesor.getId());

        // Verifica que el ID del profesor sea 1
        assertEquals(1, profesor.getId());
    }

    @Test
    void findProfesorTest() throws ProfesorNotFoundException {
        // Crear una instancia de la implementación ProfesorDaoMemoryImpl
        ProfesorDaoMemoryImpl profesorDao = new ProfesorDaoMemoryImpl();

        // Crear un profesor ficticio
        Profesor profesor = new Profesor();
        profesor.setNombre("Eduardo");
        profesor.setApellido("Ruiz");
        profesor.setTitulo("Ingeniero en Sistemas");

        // Guardar al profesor
        profesorDao.saveProfesor(profesor);

        // Buscar al profesor
        Profesor res = profesorDao.findProfesor(profesor.getId());

        // Verifica que el resultado no sea nulo
        assertNotNull(res);
        // Verifica que los datos coincidan
        assertEquals(profesor, res);
    }

    @Test
    void updateProfesorTest() throws ProfesorNotFoundException {
        // Crear una instancia de la implementación ProfesorDaoMemoryImpl
        ProfesorDaoMemoryImpl profesorDao = new ProfesorDaoMemoryImpl();

        // Crear un profesor ficticio
        Profesor profesor = new Profesor();
        profesor.setNombre("Eduardo");
        profesor.setApellido("Ruiz");
        profesor.setTitulo("Ingeniero en Sistemas");

        // Guardar al profesor
        profesorDao.saveProfesor(profesor);

        // Crear un profesor con los datos a modificar
        ProfesorDto profesorModificado = new ProfesorDto();
        profesorModificado.setNombre("Marco");
        profesorModificado.setApellido("Silva");

        // Actualizar los datos del profesor
        Profesor res = profesorDao.updateProfesor(profesor.getId(), profesorModificado);

        // Verifica que la respuesta no sea nula
        assertNotNull(res);
        // Verifica que los datos se hayan actualizado
        assertEquals(profesor.getId(), res.getId());
        assertEquals("Marco", res.getNombre());
        assertEquals("Silva", res.getApellido());
        assertEquals("Ingeniero en Sistemas", res.getTitulo());
    }

    @Test
    void deleteProfesorTest() throws ProfesorNotFoundException {
        // Crear una instancia de la implementación ProfesorDaoMemoryImpl
        ProfesorDaoMemoryImpl profesorDao = new ProfesorDaoMemoryImpl();

        // Crear un profesor ficticio
        Profesor profesor = new Profesor();
        profesor.setNombre("Eduardo");
        profesor.setApellido("Ruiz");
        profesor.setTitulo("Ingeniero en Sistemas");

        // Guardar al profesor
        profesorDao.saveProfesor(profesor);

        // Eliminar al profesor
        profesorDao.deleteProfesor(profesor.getId());

        // Verifica que se tire la excepción 'ProfesorNotFoundException' al buscar al profesor
        assertThrows(ProfesorNotFoundException.class, () -> profesorDao.findProfesor(profesor.getId()));
    }
}
