package ar.edu.utn.frbb.tup.persistence.impl;

import ar.edu.utn.frbb.tup.model.Alumno;
import ar.edu.utn.frbb.tup.persistence.AlumnoDao;
import ar.edu.utn.frbb.tup.persistence.exception.AlumnoNotFoundException;
import ar.edu.utn.frbb.tup.persistence.exception.ProfesorNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;


@Service
public class AlumnoDaoMemoryImpl implements AlumnoDao {

    // Atributos -------------------------------------------------------

    private static long contadorId = 1;

    private static Map<Long, Alumno> repositorioAlumnos = new HashMap<>();

    // -----------------------------------------------------------------

    // Métodos para operaciones CRUD de Alumnos ------------------------

    @Override
    public Alumno saveAlumno(Alumno alumno) {
        alumno.setId(contadorId++); // Establecer el ID del alumno
        repositorioAlumnos.put(alumno.getId(), alumno); // Usar el ID como clave en el mapa
        return alumno;
    }

    @Override
    public Alumno findAlumno(long idAlumno) throws AlumnoNotFoundException {
        // Verificamos si el alumno existe en el repositorio
        if (!repositorioAlumnos.containsKey(idAlumno)) {
            throw new AlumnoNotFoundException("No se encontró un alumno con el id " + idAlumno);
        }

        return repositorioAlumnos.get(idAlumno);
    }

    @Override
    public Alumno loadAlumno(Long dni) {
        return null;
    }

    @Override
    public void deleteAlumno(long idAlumno) {

    }

    // ----------------------------------------------------------------

    // Métodos para operaciones relacionadas con asignaturas ----------

    @Override
    public void cursarAsignatura(long idAlumno, int idAsignatura) {
    }

    @Override
    public void aprobarAsignatura(long idAlumno, int idAsignatura, int nota) {
    }

    @Override
    public void perderRegularidadAsignatura(long idAlumno, int idAsignatura) {
    }

    // ----------------------------------------------------------------
}
