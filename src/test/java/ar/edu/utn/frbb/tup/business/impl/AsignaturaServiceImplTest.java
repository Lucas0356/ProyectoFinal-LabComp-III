package ar.edu.utn.frbb.tup.business.impl;

import ar.edu.utn.frbb.tup.model.Asignatura;
import ar.edu.utn.frbb.tup.model.exception.AsignaturaInexistenteException;
import ar.edu.utn.frbb.tup.model.exception.CorrelatividadesNoAprobadasException;
import ar.edu.utn.frbb.tup.persistence.AlumnoDao;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

// Extensión de Spring para ejecutar pruebas de integración
@SpringBootTest
class AsignaturaServiceImplTest {

    @Mock
    private AlumnoDao alumnoDao;

    @InjectMocks
    private AsignaturaServiceImpl asignaturaService;

    // Prueba del método perderRegularidadAsignatura
    @Test
    void perderRegularidadAsignaturaTest() throws AsignaturaInexistenteException {
        long idAlumno = 1;
        long idAsignatura = 2;

        asignaturaService.perderRegularidadAsignatura(idAlumno, idAsignatura);

        // Verifica que el método perderRegularidadAsignatura en el objeto alumnoDao se haya llamado una vez con los parámetros correctos
        verify(alumnoDao, Mockito.times(1)).perderRegularidadAsignatura(idAlumno, idAsignatura);
    }

    // Prueba del método cursarAsignatura
    @Test
    void cursarAsignaturaTest() throws AsignaturaInexistenteException {
        long idAlumno = 1;
        long idAsignatura = 2;

        asignaturaService.cursarAsignatura(idAlumno, idAsignatura);

        // Verifica que el método cursarAsignatura en el objeto alumnoDao se haya llamado una vez con los parámetros correctos
        verify(alumnoDao, Mockito.times(1)).cursarAsignatura(idAlumno, idAsignatura);
    }

    // Prueba del método aprobarAsignatura
    @Test
    void aprobarAsignaturaTest() throws AsignaturaInexistenteException, CorrelatividadesNoAprobadasException {
        long idAlumno = 1;
        long idAsignatura = 2;
        int nota = 7;

        asignaturaService.aprobarAsignatura(idAlumno, nota, idAsignatura);

        // Verifica que el método aprobarAsignatura en el objeto alumnoDao se haya llamado una vez con los parámetros correctos
        verify(alumnoDao, Mockito.times(1)).aprobarAsignatura(idAlumno, nota, idAsignatura);
    }

    // Prueba del método buscarAsignatura
    @Test
    void buscarAsignaturaTest() throws AsignaturaInexistenteException {
        long idAlumno = 1;
        long idAsignatura = 2;

        // Crea una Asignatura ficticia
        Asignatura asignatura = new Asignatura();

        // Configura el comportamiento del mock para que devuelva la Asignatura ficticia
        Mockito.when(alumnoDao.getAsignaturaPorId(idAlumno, idAsignatura)).thenReturn(asignatura);

        Asignatura res = asignaturaService.buscarAsignatura(idAsignatura, idAlumno);

        // Verifica que el resultado obtenido sea igual a la Asignatura ficticia que configuramos anteriormente
        assertEquals(asignatura, res);

        // Verifica que el método getAsignaturaPorId en el objeto alumnoDao se haya llamado una vez con los parámetros correctos
        verify(alumnoDao, Mockito.times(1)).getAsignaturaPorId(idAlumno, idAsignatura);
    }

}
