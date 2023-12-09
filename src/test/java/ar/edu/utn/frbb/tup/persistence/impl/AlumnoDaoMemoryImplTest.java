package ar.edu.utn.frbb.tup.persistence.impl;

import ar.edu.utn.frbb.tup.model.Alumno;
import ar.edu.utn.frbb.tup.model.Asignatura;
import ar.edu.utn.frbb.tup.model.EstadoAsignatura;
import ar.edu.utn.frbb.tup.model.Materia;
import ar.edu.utn.frbb.tup.model.dto.AlumnoDto;
import ar.edu.utn.frbb.tup.model.exception.AsignaturaInexistenteException;
import ar.edu.utn.frbb.tup.model.exception.CorrelatividadesNoAprobadasException;
import ar.edu.utn.frbb.tup.persistence.exception.AlumnoNotFoundException;
import ar.edu.utn.frbb.tup.persistence.exception.NotaIncorrectaException;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AlumnoDaoMemoryImplTest {

    // Prueba del método saveAlumno
    @Test
    void saveAlumnoTest() {
        // Crear una instancia de la implementación AlumnoDaoMemoryImpl
        AlumnoDaoMemoryImpl alumnoDao = new AlumnoDaoMemoryImpl();

        // Crear dos materias ficticias para utilizar como materias existentes
        Materia materia1 = new Materia();
        Materia materia2 = new Materia();

        // Crear una lista de materias existentes con las materias ficticias
        List<Materia> materiasExistentes = List.of(materia1, materia2);

        // Crear un objeto Alumno
        Alumno alumno = new Alumno();

        // Llamar al método saveAlumno con el alumno y las materias existentes
        Alumno result = alumnoDao.saveAlumno(alumno, materiasExistentes);

        // Verifica que el resultado no es nulo
        assertNotNull(result);

        // Verifica que el resultado es igual al alumno original
        assertEquals(alumno, result);

        // Verifica que el ID del resultado es 1 (contando desde la inicialización de contadorId en la implementación)
        assertEquals(1, result.getId());

        // Verifica que el resultado tiene 2 asignaturas, correspondientes a las materias existentes
        assertEquals(2, result.getAsignaturas().size());
    }

    // Prueba del método findAlumno
    @Test
    void findAlumnoTest() throws AlumnoNotFoundException {
        // Crear una instancia de la implementación AlumnoDaoMemoryImpl
        AlumnoDaoMemoryImpl alumnoDao = new AlumnoDaoMemoryImpl();

        // Crear un objeto Alumno ficticio
        Alumno alumno = new Alumno();

        // Agregar el alumno ficticio al repositorio de alumnos con un ID específico
        long alumnoId = 1;
        alumno.setId(alumnoId);
        alumnoDao.saveAlumno(alumno, Collections.emptyList());

        // Llamar al método findAlumno con el ID del alumno simulado
        Alumno result = alumnoDao.findAlumno(alumnoId);

        // Verifica que el resultado no es nulo
        assertNotNull(result);

        // Verifica que el resultado es igual al alumno simulado
        assertEquals(alumno, result);
    }

    // Prueba del método findAlumno cuando el alumno no existe
    @Test
    void findAlumnoNoExistenteTest() {
        // Crear una instancia de la implementación AlumnoDaoMemoryImpl
        AlumnoDaoMemoryImpl alumnoDao = new AlumnoDaoMemoryImpl();

        // Intentar llamar al método findAlumno con un ID que no existe
        long alumnoIdNoExistente = 1;

        // Verifica que se lanza una excepción AlumnoNotFoundException
        assertThrows(AlumnoNotFoundException.class, () -> alumnoDao.findAlumno(alumnoIdNoExistente));
    }

    // Prueba del método updateAlumno
    @Test
    void updateAlumnoTest() throws AlumnoNotFoundException {
        // Crear una instancia de la implementación AlumnoDaoMemoryImpl
        AlumnoDaoMemoryImpl alumnoDao = new AlumnoDaoMemoryImpl();

        // Crear un objeto Alumno ficticio
        Alumno alumno = new Alumno();

        // Agregar el alumno ficticio al repositorio de alumnos con un ID específico
        long alumnoId = 1;
        alumno.setId(alumnoId);
        alumnoDao.saveAlumno(alumno, Collections.emptyList());

        // Crear un objeto AlumnoDto con valores modificados
        AlumnoDto alumnoModificadoDto = new AlumnoDto();
        alumnoModificadoDto.setNombre("NuevoNombre");
        alumnoModificadoDto.setApellido("NuevoApellido");
        alumnoModificadoDto.setDni(12345678);

        // Llamar al método updateAlumno con el ID del alumno ficticio y el AlumnoDto modificado
        Alumno result = alumnoDao.updateAlumno(alumnoId, alumnoModificadoDto);

        // Verificar que el resultado no es nulo
        assertNotNull(result);

        // Verificar que los campos del alumno se han actualizado correctamente
        assertEquals(alumnoModificadoDto.getNombre(), result.getNombre());
        assertEquals(alumnoModificadoDto.getApellido(), result.getApellido());
        assertEquals(alumnoModificadoDto.getDni(), result.getDni());
    }

    // Prueba del método deleteAlumno
    @Test
    void deleteAlumnoTest() throws AlumnoNotFoundException {
        // Crear una instancia de la implementación AlumnoDaoMemoryImpl
        AlumnoDaoMemoryImpl alumnoDao = new AlumnoDaoMemoryImpl();

        // Crear un objeto Alumno ficticio
        Alumno alumno = new Alumno();

        // Agregar el alumno ficticio al repositorio de alumnos con un ID específico
        long alumnoId = 1;
        alumno.setId(alumnoId);
        alumnoDao.saveAlumno(alumno, Collections.emptyList());

        // Llamar al método deleteAlumno con el ID del alumno ficticio
        alumnoDao.deleteAlumno(alumnoId);

        // Verificar que el alumno ha sido eliminado del repositorio
        assertThrows(AlumnoNotFoundException.class, () -> alumnoDao.findAlumno(alumnoId));
    }

    // Prueba del método deleteAlumno cuando el alumno no existe
    @Test
    void deleteAlumnoNoExistenteTest() {
        // Crear una instancia de la implementación AlumnoDaoMemoryImpl
        AlumnoDaoMemoryImpl alumnoDao = new AlumnoDaoMemoryImpl();

        // Intentar llamar al método deleteAlumno con un ID que no existe
        long alumnoIdNoExistente = 1;

        // Verificar que se lanza una excepción AlumnoNotFoundException
        assertThrows(AlumnoNotFoundException.class, () -> alumnoDao.deleteAlumno(alumnoIdNoExistente));
    }

    // Prueba del método aprobarAsignatura
    @Test
    void aprobarAsignaturaTest() throws AlumnoNotFoundException,
            AsignaturaInexistenteException, NotaIncorrectaException, CorrelatividadesNoAprobadasException {
        // Crear una instancia de la implementación AlumnoDaoMemoryImpl
        AlumnoDaoMemoryImpl alumnoDao = new AlumnoDaoMemoryImpl();

        // Crear un objeto Alumno simulado
        Alumno alumno = new Alumno();

        // Crear una materia simulada
        Materia materia = new Materia();

        // Agregar el alumno simulado al repositorio de alumnos con un ID específico
        long alumnoId = 1;
        alumno.setId(alumnoId);
        alumnoDao.saveAlumno(alumno, Collections.singletonList(materia));

        // Obtener la asignatura asociada a la materia simulada (que comparte el mismo ID)
        Asignatura asignatura = alumno.getAsignaturas().get(0);

        // Cursar la materia
        alumnoDao.cursarAsignatura(alumnoId, materia.getMateriaId());

        // Llamar al método aprobarAsignatura con el ID del alumno simulado, una nota válida y el ID de la materia (que comparte el mismo ID que la asignatura)
        int notaAprobacion = 7;
        alumnoDao.aprobarAsignatura(alumnoId, notaAprobacion, materia.getMateriaId());

        // Verificar que el estado de la asignatura ha cambiado a APROBADA y se ha asignado la nota
        assertEquals(EstadoAsignatura.APROBADA, asignatura.getEstado());
        assertEquals(notaAprobacion, asignatura.getNota());
    }

    @Test
    void cursarAsignaturaTest() throws AlumnoNotFoundException, AsignaturaInexistenteException {
        // Crear una instancia de la implementación AlumnoDaoMemoryImpl
        AlumnoDaoMemoryImpl alumnoDao = new AlumnoDaoMemoryImpl();

        // Crear un objeto Alumno ficticio
        Alumno alumno = new Alumno();

        // Crear una materia ficticia
        Materia materia = new Materia();

        // Agregar el alumno ficticio al repositorio de alumnos con un ID específico
        long alumnoId = 1;
        alumno.setId(alumnoId);
        alumnoDao.saveAlumno(alumno, Collections.singletonList(materia));

        // Obtener la asignatura asociada a la materia ficticia (que comparte el mismo ID)
        Asignatura asignatura = alumno.getAsignaturas().get(0);

        // Llamar al método cursarAsignatura con el ID del alumno ficticio y el ID de la materia
        // (que comparte el mismo ID que la asignatura)
        alumnoDao.cursarAsignatura(alumnoId, materia.getMateriaId());

        // Verificar que el estado de la asignatura ha cambiado a CURSADA
        assertEquals(EstadoAsignatura.CURSADA, asignatura.getEstado());
    }

    @Test
    void perderRegularidadAsignaturaTest() throws AsignaturaInexistenteException, AlumnoNotFoundException {
        // Crear una instancia de la implementación AlumnoDaoMemoryImpl
        AlumnoDaoMemoryImpl alumnoDao = new AlumnoDaoMemoryImpl();

        // Crear un objeto Alumno ficticio
        Alumno alumno = new Alumno();

        // Crear una materia ficticia
        Materia materia = new Materia();

        // Agregar el alumno ficticio al repositorio de alumnos con un ID específico
        long alumnoId = 1;
        alumno.setId(alumnoId);
        alumnoDao.saveAlumno(alumno, Collections.singletonList(materia));

        // Obtener la asignatura asociada a la materia ficticia (que comparte el mismo ID)
        Asignatura asignatura = alumno.getAsignaturas().get(0);

        // Cursar la asignatura primero
        alumnoDao.cursarAsignatura(alumnoId, materia.getMateriaId());

        // Llamar al método perderRegularidadAsignatura con el ID del alumno ficticio y el ID de la materia
        // (que comparte el mismo ID que la asignatura)
        alumnoDao.perderRegularidadAsignatura(alumnoId, materia.getMateriaId());

        // Verificar que el estado de la asignatura ha cambiado a NO_CURSADA
        assertEquals(EstadoAsignatura.NO_CURSADA, asignatura.getEstado());
    }

}