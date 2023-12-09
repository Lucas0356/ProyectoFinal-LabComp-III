package ar.edu.utn.frbb.tup.business.impl;

import ar.edu.utn.frbb.tup.business.ProfesorService;
import ar.edu.utn.frbb.tup.model.Materia;
import ar.edu.utn.frbb.tup.model.Profesor;
import ar.edu.utn.frbb.tup.model.dto.MateriaDto;
import ar.edu.utn.frbb.tup.persistence.MateriaDao;
import ar.edu.utn.frbb.tup.persistence.exception.MateriaNotFoundException;
import ar.edu.utn.frbb.tup.persistence.exception.ProfesorNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class MateriaServiceImplTest {

    @Mock
    private MateriaDao materiaDao;

    @Mock
    private ProfesorService profesorService;

    @InjectMocks
    private MateriaServiceImpl materiaService;

    // Prueba del método crearMateria
    @Test
    void crearMateriaTest() throws ProfesorNotFoundException {
        // Crea un objeto Materia ficticio
        MateriaDto materiaDto = new MateriaDto();
        materiaDto.setNombre("Matematica");
        materiaDto.setAnio(3);
        materiaDto.setCuatrimestre(1);
        materiaDto.setProfesorId(1);
        materiaDto.setCorrelatividades(Arrays.asList(2, 3));

        // Crea un objeto Profesor ficticio y configura el mock para que lo devuelva cuando
        // se llame a buscarProfesor con el ID ficticio
        Profesor profesor = new Profesor();
        when(profesorService.buscarProfesor(1)).thenReturn(profesor);

        Materia result = materiaService.crearMateria(materiaDto);

        // Verifica que el resultado no sea nulo
        assertNotNull(result);
        // Verifica que el nombre de la materia coincida con el esperado
        assertEquals("Matematica", result.getNombre());
        // Verifica que el año de la materia coincida con el esperado
        assertEquals(3, result.getAnio());
        // Verifica que el cuatrimestre de la materia coincida con el esperado
        assertEquals(1, result.getCuatrimestre());
        // Verifica que el profesor de la materia coincida con el profesor ficticio configurado
        assertEquals(profesor, result.getProfesor());
        // Verifica que las correlatividades de la materia coincidan con las esperadas
        assertEquals(Arrays.asList(2, 3), result.getCorrelatividades());

        // Verifica que el método saveMateria en el objeto materiaDao se haya llamado una vez con los parámetros correctos
        verify(materiaDao, times(1)).saveMateria(any(Materia.class));
    }

    // Prueba del método obtenerTodasLasMaterias
    @Test
    void obtenerTodasLasMateriasTest() {
        // Crea una lista de materias ficticias
        List<Materia> materias = Arrays.asList(new Materia(), new Materia());

        // Configura el comportamiento del mock para que devuelva la lista de materias ficticias
        when(materiaDao.obtenerTodasLasMaterias()).thenReturn(materias);

        // Llama al método obtenerTodasLasMaterias del materiaService
        List<Materia> result = materiaService.obtenerTodasLasMaterias();

        // Verifica que el resultado tenga la misma cantidad de elementos que la lista ficticia
        assertEquals(2, result.size());

        // Verifica que el método obtenerTodasLasMaterias en el objeto materiaDao se haya llamado una vez
        verify(materiaDao, times(1)).obtenerTodasLasMaterias();
    }

    // Prueba del método buscarMateria
    @Test
    void buscarMateriaTest() throws MateriaNotFoundException {
        // Crea un ID ficticio para la materia que se va a buscar
        int idMateria = 1;

        // Crea un objeto Materia ficticia
        Materia materia = new Materia();

        // Configura el comportamiento del mock para que, cuando se llame a findMateria con el ID ficticio,
        // devuelva el objeto Materia ficticio creado anteriormente
        when(materiaDao.findMateria(idMateria)).thenReturn(materia);

        // Llama al método buscarMateria del materiaService con el ID ficticio
        Materia result = materiaService.buscarMateria(idMateria);

        // Verifica que el resultado de buscarMateria coincide con lo esperado
        assertEquals(materia, result);

        // Verifica que el método findMateria en el objeto materiaDao se haya llamado una vez con el ID ficticio
        verify(materiaDao, times(1)).findMateria(idMateria);
    }

}
