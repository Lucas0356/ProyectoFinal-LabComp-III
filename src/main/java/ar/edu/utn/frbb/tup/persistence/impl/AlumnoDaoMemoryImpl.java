package ar.edu.utn.frbb.tup.persistence.impl;

import ar.edu.utn.frbb.tup.model.Alumno;
import ar.edu.utn.frbb.tup.persistence.AlumnoDao;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;


@Service
public class AlumnoDaoMemoryImpl implements AlumnoDao {

    // Atributos -------------------------------------------------------

    private static Map<Long, Alumno> repositorioAlumnos = new HashMap<>();

    // -----------------------------------------------------------------

    // Métodos para operaciones CRUD de Alumnos ------------------------

    @Override
    public Alumno saveAlumno(Alumno alumno) {
        Random random = new Random();
        alumno.setId(random.nextLong());
        return repositorioAlumnos.put(alumno.getDni(), alumno);
    }

    @Override
    public Alumno findAlumno(long idAlumno) {
        for (Alumno a: repositorioAlumnos.values()) {
            if (a.getId() == (idAlumno)){
                return a;
            }
        }
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "No existe ningún alumno con ese ID."
        );
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
