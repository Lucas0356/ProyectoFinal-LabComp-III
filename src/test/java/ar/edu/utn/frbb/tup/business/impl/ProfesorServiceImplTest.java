package ar.edu.utn.frbb.tup.business.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import ar.edu.utn.frbb.tup.model.Materia;
import ar.edu.utn.frbb.tup.model.Profesor;
import ar.edu.utn.frbb.tup.model.dto.ProfesorDto;
import ar.edu.utn.frbb.tup.persistence.MateriaDao;
import ar.edu.utn.frbb.tup.persistence.ProfesorDao;
import ar.edu.utn.frbb.tup.persistence.exception.MateriaNotFoundException;
import ar.edu.utn.frbb.tup.persistence.exception.ProfesorNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
class ProfesorServiceImplTest {

    @Mock
    private ProfesorDao profesorDao;

    @Mock
    private MateriaDao materiaDao;

    @InjectMocks
    private ProfesorServiceImpl profesorService;

    // Prueba del método crearProfesor
    @Test
    void crearProfesorTest() {
        // Crea un Profesor ficticio
        ProfesorDto profesorDto = new ProfesorDto();
        profesorDto.setNombre("John");
        profesorDto.setApellido("Doe");
        profesorDto.setTitulo("PhD");

        // Configura el comportamiento del mock para que devuelva el Profesor ficticio
        Profesor result = profesorService.crearProfesor(profesorDto);

        // Verificamos si el resultado coincide con las expectativas
        assertEquals(profesorDto.getNombre(), result.getNombre()); // Verifica que el nombre del profesor sea el esperado
        assertEquals(profesorDto.getApellido(), result.getApellido()); // Verifica que el apellido del profesor sea el esperado
        assertEquals(profesorDto.getTitulo(), result.getTitulo()); // Verifica que el título del profesor sea el esperado

        // Verifica que el método saveProfesor en el objeto profesorDao se haya llamado una vez con los parámetros correctos
        verify(profesorDao, times(1)).saveProfesor(any(Profesor.class));
    }

    // Prueba del método buscarProfesor
    @Test
    void buscarProfesorTest() throws ProfesorNotFoundException {
        // Crea un ID ficticio para el profesor que se va a buscar
        long idProfesor = 1;

        // Crea un objeto Profesor ficticio
        Profesor profesor = new Profesor();

        // Configura el comportamiento del mock para que, cuando se llame a findProfesor con el ID ficticio,
        // devuelva el objeto Profesor ficticio creado anteriormente
        when(profesorDao.findProfesor(idProfesor)).thenReturn(profesor);

        // Llama al método buscarProfesor del profesorService con el ID ficticio
        Profesor result = profesorService.buscarProfesor(idProfesor);

        // Verifica que el resultado de buscarProfesor coincide con el Profesor ficticio esperado
        assertEquals(profesor, result);
    }

    // Prueba del método modificarProfesor
    @Test
    void modificarProfesorTest() throws ProfesorNotFoundException {
        // Crea un ID ficticio para el profesor que se va a modificar
        long idProfesor = 1;

        // Crea un objeto ProfesorDto ficticio con datos de modificación
        ProfesorDto profesorModificado = new ProfesorDto();
        profesorModificado.setNombre("Marco");

        // Crea un objeto Profesor ficticio
        Profesor profesorExistente = new Profesor();

        // Configura el comportamiento del mock para que, cuando se llame a updateProfesor con el ID ficticio
        // y el objeto ProfesorDto modificado, devuelva un objeto Profesor
        when(profesorDao.updateProfesor(idProfesor, profesorModificado)).thenReturn(profesorExistente);

        // Llama al método modificarProfesor del profesorService con el ID ficticio y el ProfesorDto modificado
        Profesor result = profesorService.modificarProfesor(idProfesor, profesorModificado);

        // Verifica que el resultado de modificarProfesor coincida con el esperado
        assertEquals(profesorExistente, result);
    }

    // Prueba del método borrarProfesor
    @Test
    void borrarProfesorTest() throws ProfesorNotFoundException, MateriaNotFoundException {
        // Crea un ID ficticio para el profesor que se va a borrar
        long idProfesor = 1;

        // Crea un objeto Profesor ficticio y configura el mock para que lo devuelva cuando
        // se llame a findProfesor con el ID ficticio
        Profesor profesor = new Profesor();
        profesor.setId(idProfesor);
        when(profesorDao.findProfesor(idProfesor)).thenReturn(profesor);

        // Crea dos objetos Materia ficticios con ID ficticio y configura el mock para
        // que los devuelva cuando se llame a findMateria con los IDs ficticios en la lista
        Materia materia1 = new Materia();
        materia1.setMateriaId(1);
        Materia materia2 = new Materia();
        materia2.setMateriaId(2);
        List<Integer> materiasDictadasIDs = Arrays.asList(1, 2);
        profesor.setMateriasDictadas(materiasDictadasIDs);
        when(materiaDao.findMateria(1)).thenReturn(materia1);
        when(materiaDao.findMateria(2)).thenReturn(materia2);

        // Act
        // Llama al método borrarProfesor del profesorService con el ID ficticio
        String result = profesorService.borrarProfesor(idProfesor);

        // Assert
        // Verifica que el resultado del método coincide con el mensaje esperado
        assertEquals("El profesor con el id 1 ha sido eliminado correctamente, junto con sus materias dictadas.", result);

        // Verifica que el método deleteMateria en el objeto materiaDao se haya llamado
        // una vez para cada ID de materia en la lista ficticia
        verify(materiaDao, times(1)).deleteMateria(1);
        verify(materiaDao, times(1)).deleteMateria(2);

        // Verifica que el método deleteProfesor en el objeto profesorDao se haya llamado
        // una vez con el ID ficticio
        verify(profesorDao, times(1)).deleteProfesor(idProfesor);
    }

    // Prueba del método obtenerMateriasDictadasProfesor
    @Test
    void obtenerMateriasDictadasProfesorTest() throws ProfesorNotFoundException, MateriaNotFoundException {
        // Crea un ID ficticio para el profesor del cual se obtendrán las materias dictadas
        long idProfesor = 1;

        // Crea un objeto Profesor ficticio y configura el mock para que lo devuelva cuando
        // se llame a findProfesor con el ID ficticio
        Profesor profesor = new Profesor();
        profesor.setId(idProfesor);
        when(profesorDao.findProfesor(idProfesor)).thenReturn(profesor);

        // Crea dos objetos Materia ficticios con nombres y configura el mock para que los devuelva
        // cuando se llame a findMateria con los IDs ficticios
        Materia materia1 = new Materia();
        materia1.setNombre("Matematica");
        Materia materia2 = new Materia();
        materia2.setNombre("Fisica");
        List<Integer> materiasDictadasIDs = Arrays.asList(1, 2);
        profesor.setMateriasDictadas(materiasDictadasIDs);
        when(materiaDao.findMateria(1)).thenReturn(materia1);
        when(materiaDao.findMateria(2)).thenReturn(materia2);

        // Llama al método obtenerMateriasDictadasProfesor del profesorService con el ID ficticio
        List<Materia> result = profesorService.obtenerMateriasDictadasProfesor(idProfesor);

        // Verifica que el resultado del método coincide con la cantidad de materias ficticias y sus nombres esperados
        assertEquals(2, result.size());
        assertEquals("Fisica", result.get(0).getNombre());
        assertEquals("Matematica", result.get(1).getNombre());
    }

}
