package ar.edu.utn.frbb.tup.persistence.impl;

import ar.edu.utn.frbb.tup.model.*;
import ar.edu.utn.frbb.tup.model.dto.AlumnoDto;
import ar.edu.utn.frbb.tup.model.exception.AsignaturaInexistenteException;
import ar.edu.utn.frbb.tup.persistence.AlumnoDao;
import ar.edu.utn.frbb.tup.persistence.exception.AlumnoNotFoundException;
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
    public Alumno updateAlumno(long idAlumno, AlumnoDto alumnoModificado) throws AlumnoNotFoundException {

        // Verificamos si el profesor existe en el repositorio
        if (!repositorioAlumnos.containsKey(idAlumno)) {
            throw new AlumnoNotFoundException("No se encontró ningún alumno con el id " + idAlumno);
        }

        // Obtenemos el profesor ya existente
        Alumno alumno = repositorioAlumnos.get(idAlumno);

        // Obtenemos los nuevos valores de nombre, apellido y título del alumno
        String nuevoNombre = alumnoModificado.getNombre();
        String nuevoApellido = alumnoModificado.getApellido();
        long nuevoDni = alumnoModificado.getDni();

        // Actualizamos los campos del alumno existente si es que no son nulos o vacíos
        if (nuevoNombre != null && !nuevoNombre.isEmpty()) {
            alumno.setNombre(nuevoNombre);
        }

        if (nuevoApellido != null && !nuevoApellido.isEmpty()) {
            alumno.setApellido(nuevoApellido);
        }

        if (nuevoDni != 0) {
            alumno.setDni(nuevoDni);
        }

        return alumno;
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
    public void actualizarAsignaturasAlumnos(Materia nuevaMateria) {

        Asignatura nuevaAsignatura = new Asignatura(nuevaMateria);

        for (Alumno alumno : repositorioAlumnos.values()) {
            alumno.getAsignaturas().add(nuevaAsignatura);

            // Actualizamos el alumno en la base de datos
            repositorioAlumnos.put(alumno.getId(), alumno);
        }
    }

    // ----------------------------------------------------------------
}
