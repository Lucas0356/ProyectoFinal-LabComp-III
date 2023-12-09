package ar.edu.utn.frbb.tup.business.impl;

import ar.edu.utn.frbb.tup.business.AsignaturaService;
import ar.edu.utn.frbb.tup.model.Alumno;
import ar.edu.utn.frbb.tup.model.EstadoAsignatura;
import ar.edu.utn.frbb.tup.model.Materia;
import ar.edu.utn.frbb.tup.model.dto.AlumnoDto;
import ar.edu.utn.frbb.tup.model.exception.AsignaturaInexistenteException;
import ar.edu.utn.frbb.tup.persistence.AlumnoDao;
import ar.edu.utn.frbb.tup.persistence.exception.AlumnoNotFoundException;
import ar.edu.utn.frbb.tup.business.exceptions.DniInvalidoException;
import ar.edu.utn.frbb.tup.business.exceptions.IdInvalidoException;
import ar.edu.utn.frbb.tup.business.exceptions.NombreInvalidoException;
import ar.edu.utn.frbb.tup.business.exceptions.ApellidoInvalidoException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

// Extensión de Spring para ejecutar pruebas de integración
@SpringBootTest
class AlumnoServiceImplTest {

    @Mock
    private AlumnoDao alumnoDao;

    @Mock
    private AsignaturaService asignaturaService;

    @InjectMocks
    private AlumnoServiceImpl alumnoService;

    // Prueba del método crearAlumno
    @Test
    void crearAlumnoTest() {
        // Crear un objeto AlumnoDto para la solicitud
        AlumnoDto alumnoDto = new AlumnoDto();
        alumnoDto.setNombre("Lucas");
        alumnoDto.setApellido("Moltedo");
        alumnoDto.setDni(44881416);

        // Crea una lista vacía de materias existentes
        List<Materia> materiasExistentes = Collections.emptyList();

        // Llamamos al método que se está probando (crearAlumno) con los datos proporcionados
        Alumno res = alumnoService.crearAlumno(alumnoDto, materiasExistentes);

        // Verificamos si el resultado coincide con las expectativas
        assertEquals(alumnoDto.getNombre(), res.getNombre()); // Verifica que el nombre del alumno sea el esperado
        assertEquals(alumnoDto.getApellido(), res.getApellido()); // Verifica que el apellido del alumno sea el esperado
        assertEquals(alumnoDto.getDni(), res.getDni()); // Verifica que el DNI del alumno sea el esperado

        // Verifica que el método saveAlumno en el objeto alumnoDao se haya llamado una vez con los parámetros correctos
        verify(alumnoDao, Mockito.times(1)).saveAlumno(any(Alumno.class), any(List.class));
    }

    // Prueba del método crearAlumno con DNI inválido
    @Test
    void crearAlumnoConDniInvalidoTest() {
        // Crear un objeto AlumnoDto para la solicitud (con un DNI inválido)
        AlumnoDto alumnoDto = new AlumnoDto();
        alumnoDto.setNombre("Lucas");
        alumnoDto.setApellido("Moltedo");
        alumnoDto.setDni(0);

        // Crea una lista vacía de materias existentes
        List<Materia> materiasExistentes = Collections.emptyList();

        // Verifica que se haya tirado una excepción 'DniInvalidoException'
        assertThrows(DniInvalidoException.class, () -> alumnoService.crearAlumno(alumnoDto, materiasExistentes));
    }

    // Prueba del método crearAlumno con nombre inválido
    @Test
    void crearAlumnoConNombreInvalidoTest() {
        // Crear un objeto AlumnoDto para la solicitud (con un nombre inválido, no se admiten números)
        AlumnoDto alumnoDto = new AlumnoDto();
        alumnoDto.setNombre("200");
        alumnoDto.setApellido("Moltedo");
        alumnoDto.setDni(44881416);

        // Crea una lista vacía de materias existentes
        List<Materia> materiasExistentes = Collections.emptyList();

        // Verifica que se haya tirado una excepción 'NombreInvalidoException'
        assertThrows(NombreInvalidoException.class, () -> alumnoService.crearAlumno(alumnoDto, materiasExistentes));
    }

    // Prueba del método crearAlumno con apellido inválido
    @Test
    void crearAlumnoConApellidoInvalidoTest() {
        // Crear un objeto AlumnoDto para la solicitud (con un apellido inválido, no se admiten números)
        AlumnoDto alumnoDto = new AlumnoDto();
        alumnoDto.setNombre("Lucas");
        alumnoDto.setApellido("555");
        alumnoDto.setDni(44881416);

        // Crea una lista vacía de materias existentes
        List<Materia> materiasExistentes = Collections.emptyList();

        // Verifica que se haya tirado una excepción 'ApellidoInvalidoException'
        assertThrows(ApellidoInvalidoException.class, () -> alumnoService.crearAlumno(alumnoDto, materiasExistentes));
    }

    // Prueba del método buscarAlumno
    @Test
    void buscarAlumnoTest() {
        long idAlumno = 1;

        // Creamos un objeto Alumno ficticio
        Alumno alumno = new Alumno();

        // Configura el comportamiento del mock para que devuelva el objeto Alumno ficticio
        Mockito.when(alumnoDao.findAlumno(idAlumno)).thenReturn(alumno);

        // Llamamos al método buscarAlumno con el ID proporcionado
        Alumno res = alumnoService.buscarAlumno(idAlumno);

        // Verifica que el resultado obtenido sea igual al objeto Alumno que creamos anteriormente
        assertEquals(alumno, res);
    }

    // Prueba del método buscarAlumno con ID inválido
    @Test
    void buscarAlumnoConIdInvalidoTest() {
        long idAlumno = 0;

        // Verifica que al llamar a buscarAlumno con un ID inválido se arroje una excepción 'IdInvalidoException'
        assertThrows(IdInvalidoException.class, () -> alumnoService.buscarAlumno(idAlumno));
    }

    // Prueba del método modificarAlumno
    @Test
    void modificarAlumnoTest() {
        long idAlumno = 1;

        // Crear un objeto AlumnoDto con los datos a modificar
        AlumnoDto alumnoModificado = new AlumnoDto();
        alumnoModificado.setNombre("Juan Cruz");
        alumnoModificado.setApellido("Alarcon");
        alumnoModificado.setDni(12345678);

        // Crear un objeto Alumno ficticio
        Alumno alumnoExistente = new Alumno();

        // Configura el comportamiento del mock para que devuelva el objeto Alumno ficticio al llamar a updateAlumno
        Mockito.when(alumnoDao.updateAlumno(idAlumno, alumnoModificado)).thenReturn(alumnoExistente);

        // Llamamos al método que se está probando (modificarAlumno) con los datos proporcionados
        Alumno res = alumnoService.modificarAlumno(idAlumno, alumnoModificado);

        // Verifica que el resultado obtenido sea igual al objeto Alumno ficticio que configuramos anteriormente
        assertEquals(alumnoExistente, res);
    }

    // Prueba del método modificarAlumno con datos inválidos
    @Test
    void modificarAlumnoConDatosInvalidosTest() {
        // Arrange
        long idAlumno = 1;

        // Crear un objeto AlumnoDto con datos inválidos (nombre y apellido con número, DNI inválido)
        AlumnoDto alumnoModificado = new AlumnoDto();
        alumnoModificado.setNombre("123");
        alumnoModificado.setApellido("456");
        alumnoModificado.setDni(-5);

        // Verifica que al llamar a modificarAlumno con datos inválidos se arroje una excepción 'NombreInvalidoException'
        assertThrows(NombreInvalidoException.class, () -> alumnoService.modificarAlumno(idAlumno, alumnoModificado));
    }

    // Prueba del método borrarAlumno
    @Test
    void borrarAlumnoTest() {
        long idAlumno = 1;

        // Crear un objeto Alumno ficticio
        Alumno alumnoExistente = new Alumno();

        // Configura el comportamiento del mock para que devuelva el objeto Alumno ficticio
        Mockito.when(alumnoDao.findAlumno(idAlumno)).thenReturn(alumnoExistente);

        // Llamamos al método borrarAlumno con el ID proporcionado
        String res = alumnoService.borrarAlumno(idAlumno);

        // Verifica que el resultado sea el mensaje esperado indicando que el alumno ha sido eliminado correctamente
        assertEquals("El alumno con el id " + idAlumno + " ha sido eliminado correctamente.", res);

        // Verifica que el método deleteAlumno en el objeto alumnoDao se haya llamado una vez con el ID correcto
        verify(alumnoDao, Mockito.times(1)).deleteAlumno(idAlumno);
    }

    // Prueba del método borrarAlumnoNoExistente
    @Test
    void borrarAlumnoNoExistenteTest() {
        long idAlumno = 1;

        // Configura el comportamiento del mock para que arroje una excepción 'AlumnoNotFoundException'
        Mockito.when(alumnoDao.findAlumno(idAlumno)).thenThrow(new AlumnoNotFoundException("alumno inexistente"));

        // Verifica que al llamar a borrarAlumno con un ID de alumno inexistente se arroje una excepción 'AlumnoNotFoundException'
        assertThrows(AlumnoNotFoundException.class, () -> alumnoService.borrarAlumno(idAlumno));
    }

    // Prueba del método buscarEstadoAsignatura
    @Test
    void buscarEstadoAsignaturaTest() throws AsignaturaInexistenteException {
        long idAlumno = 1;
        long idAsignatura = 2;

        EstadoAsignatura estadoAsignatura = EstadoAsignatura.CURSADA;

        // Configura el comportamiento del mock para que devuelva el estado de asignatura proporcionado
        Mockito.when(alumnoDao.getEstadoAsignaturaPorId(idAlumno, idAsignatura)).thenReturn(estadoAsignatura);

        // Llamamos al método buscarEstadoAsignatura con los IDs proporcionados
        EstadoAsignatura res = alumnoService.buscarEstadoAsignatura(idAlumno, idAsignatura);

        // Verificamos que el resultado obtenido sea igual al estado de asignatura proporcionado
        assertEquals(estadoAsignatura, res);
    }

    // Prueba del método buscarEstadoAsignatura con ID de alumno inválido
    @Test
    void buscarEstadoAsignaturaConIdAlumnoInvalidoTest() {
        long idAlumno = 0;
        long idAsignatura = 2;

        // Verificamos que al llamar a buscarEstadoAsignatura con un ID de alumno inválido se arroje una excepción 'IdInvalidoException'
        assertThrows(IdInvalidoException.class, () -> alumnoService.buscarEstadoAsignatura(idAlumno, idAsignatura));
    }

    // Prueba del método buscarEstadoAsignatura con ID de asignatura inválido
    @Test
    void buscarEstadoAsignaturaConIdAsignaturaInvalidoTest() {
        long idAlumno = 1;
        long idAsignatura = 0;

        // Verificamos que al llamar a buscarEstadoAsignatura con un ID de asignatura inválido se arroje una excepción 'IdInvalidoException'
        assertThrows(IdInvalidoException.class, () -> alumnoService.buscarEstadoAsignatura(idAlumno, idAsignatura));
    }

    // Prueba del método actualizarAsignaturasAlumnos
    @Test
    void actualizarAsignaturasAlumnosTest() {
        // Creamos un objeto de tipo Materia para la prueba
        Materia materia = new Materia();

        // Llamamos al método que se está probando (actualizarAsignaturasAlumnos) con la materia proporcionada
        alumnoService.actualizarAsignaturasAlumnos(materia);

        // Verificamos que el método actualizarAsignaturasAlumnos en el objeto alumnoDao se haya llamado una vez con los parámetros correctos
        verify(alumnoDao, Mockito.times(1)).actualizarAsignaturasAlumnos(materia);
    }
}
