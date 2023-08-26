package ar.edu.utn.frbb.tup.persistence.impl;

import ar.edu.utn.frbb.tup.model.Alumno;
import ar.edu.utn.frbb.tup.model.Asignatura;
import ar.edu.utn.frbb.tup.model.EstadoAsignatura;
import ar.edu.utn.frbb.tup.model.Materia;
import ar.edu.utn.frbb.tup.model.exception.AsignaturaInexistenteException;
import ar.edu.utn.frbb.tup.persistence.AlumnoDao;
import ar.edu.utn.frbb.tup.persistence.MateriaDao;
import ar.edu.utn.frbb.tup.persistence.exception.AlumnoNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class AlumnoDaoMemoryImpl implements AlumnoDao {

    // Atributos -------------------------------------------------------

    private static long contadorId = 1;

    private static Map<Long, Alumno> repositorioAlumnos = new HashMap<>();

    // -----------------------------------------------------------------

    // Métodos para operaciones CRUD de Alumnos ------------------------

    @Override
    public Alumno saveAlumno(Alumno alumno, List<Materia> materiasExistentes) {

        alumno.setId(contadorId++); // Establecer el ID del alumno

        // Creamos una lista de asignaturas para el alumno con todas las materias
        // existentes hasta el momento
        List<Asignatura> asignaturas = new ArrayList<>();
        for (Materia materia : materiasExistentes) {
            asignaturas.add(new Asignatura(materia));
        }
        alumno.setAsignaturas(asignaturas);

        // Usamos el ID como clave en el mapa y lo guardamos
        repositorioAlumnos.put(alumno.getId(), alumno);

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
    public void cursarAsignatura(long idAlumno, int idAsignatura) throws AlumnoNotFoundException {
        Alumno alumno = findAlumno(idAlumno);

    }

    @Override
    public void aprobarAsignatura(long idAlumno, int idAsignatura, int nota) {
    }

    @Override
    public void perderRegularidadAsignatura(long idAlumno, int idAsignatura) {
    }

    @Override
    public EstadoAsignatura getEstadoAsignaturaPorId(long idAlumno, long idAsignatura) throws AlumnoNotFoundException,
            AsignaturaInexistenteException {

        // Buscamos al alumno
        Alumno alumno = findAlumno(idAlumno);

        // Retornamos el estado de la asignatura (si existe)
        return alumno.getEstadoAsignaturaAlumno(idAsignatura);
    }

    @Override
    public void agregarNuevaAsignaturaAAlumnos(Asignatura nuevaAsignatura) {

        for (Alumno alumno : repositorioAlumnos.values()) {
            alumno.getAsignaturas().add(nuevaAsignatura);

            // Actualizamos el alumno en la base de datos
            actualizarAlumno(alumno);
        }
    }

    // ----------------------------------------------------------------

    // Métodos auxiliares ---------------------------------------------

    @Override
    public void actualizarAlumno(Alumno alumno) {
        repositorioAlumnos.put(alumno.getId(), alumno);
    }

    // ----------------------------------------------------------------
}
