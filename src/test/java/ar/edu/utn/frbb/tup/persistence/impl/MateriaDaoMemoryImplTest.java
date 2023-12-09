package ar.edu.utn.frbb.tup.persistence.impl;

import ar.edu.utn.frbb.tup.model.Materia;
import ar.edu.utn.frbb.tup.model.Profesor;
import ar.edu.utn.frbb.tup.persistence.exception.MateriaNotFoundException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MateriaDaoMemoryImplTest {

    // Prueba del método findMateria
    @Test
    void findMateriaTest() throws MateriaNotFoundException {
        // Crear una instancia de la implementación MateriaDaoMemoryImpl
        MateriaDaoMemoryImpl materiaDao = new MateriaDaoMemoryImpl();

        // Crear una Materia ficticia
        Materia materia = new Materia();

        // Asignar un profesor ficticio a la materia
        Profesor profesor = new Profesor();
        materia.setProfesor(profesor);

        materiaDao.saveMateria(materia);

        // Buscar la materia recién creada por su ID
        Materia res = materiaDao.findMateria(materia.getMateriaId());

        // Verifica que la materia recuperada no sea nula
        assertNotNull(res);
        // Verifica que la materia recuperada sea la misma que la creada
        assertEquals(materia, res);
    }

    // Prueba del método saveMateria
    @Test
    void saveMateriaTest() {
        // Crear una instancia de la implementación MateriaDaoMemoryImpl
        MateriaDaoMemoryImpl materiaDao = new MateriaDaoMemoryImpl();

        // Crear una instancia de Materia
        Materia materia = new Materia();

        // Asignar un profesor ficticio a la materia
        Profesor profesor = new Profesor();
        materia.setProfesor(profesor);

        // Guardar la materia y obtener el resultado
        Materia result = materiaDao.saveMateria(materia);

        // Asegurarse de que el resultado no sea nulo
        assertNotNull(result);

        // Asegurarse de que la materia guardada sea la misma que la creada
        assertEquals(materia, result);

        // Asegurarse de que se haya asignado un ID a la materia
        assertTrue(materia.getMateriaId() > 0);
    }

    // Prueba del método deleteMateria
    @Test
    void deleteMateriaTest() throws MateriaNotFoundException {
        // Crear una instancia de la implementación MateriaDaoMemoryImpl
        MateriaDaoMemoryImpl materiaDao = new MateriaDaoMemoryImpl();

        // Crear una instancia de Materia
        Materia materia = new Materia();

        // Asignar un profesor ficticio a la materia
        Profesor profesor = new Profesor();
        materia.setProfesor(profesor);

        // Guardar la materia en el repositorio
        materiaDao.saveMateria(materia);

        // Borrar la materia y obtener el resultado
        materiaDao.deleteMateria(materia.getMateriaId());

        // Asegurarse de que se lance una excepción MateriaNotFoundException al intentar encontrar
        // la materia borrada
        assertThrows(MateriaNotFoundException.class, () -> materiaDao.findMateria(materia.getMateriaId()));
    }
}
