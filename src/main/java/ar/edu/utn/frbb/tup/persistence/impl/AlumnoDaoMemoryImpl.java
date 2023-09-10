package ar.edu.utn.frbb.tup.persistence.impl;

import ar.edu.utn.frbb.tup.model.*;
import ar.edu.utn.frbb.tup.model.dto.AlumnoDto;
import ar.edu.utn.frbb.tup.model.dto.AsignaturaDto;
import ar.edu.utn.frbb.tup.model.exception.AsignaturaInexistenteException;
import ar.edu.utn.frbb.tup.model.exception.CorrelatividadesNoAprobadasException;
import ar.edu.utn.frbb.tup.model.exception.EstadoIncorrectoException;
import ar.edu.utn.frbb.tup.persistence.exception.NotaIncorrectaException;
import ar.edu.utn.frbb.tup.persistence.AlumnoDao;
import ar.edu.utn.frbb.tup.persistence.exception.AlumnoNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ar.edu.utn.frbb.tup.model.EstadoAsignatura.*;


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
        verificarExistenciaAlumno(idAlumno);

        return repositorioAlumnos.get(idAlumno);
    }

    @Override
    public Alumno updateAlumno(long idAlumno, AlumnoDto alumnoModificado) throws AlumnoNotFoundException {

        // Verificamos si el alumno existe en el repositorio
        verificarExistenciaAlumno(idAlumno);

        // Obtenemos el alumno ya existente
        Alumno alumno = repositorioAlumnos.get(idAlumno);

        // Obtenemos los nuevos valores de nombre, apellido y dni del alumno
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
    public void deleteAlumno(long idAlumno) throws AlumnoNotFoundException {

        // Verificamos si el alumno existe en el repositorio
        verificarExistenciaAlumno(idAlumno);

        // Borramos al alumno por su key (id)
        repositorioAlumnos.remove(idAlumno);

    }

    // ----------------------------------------------------------------

    // Métodos para operaciones relacionadas con asignaturas ----------

    @Override
    public void cursarAsignatura(long idAlumno, long idAsignatura) throws AlumnoNotFoundException,
            AsignaturaInexistenteException {

        // Buscamos la asignatura por su id
        Asignatura asignaturaActual = getAsignaturaPorId(idAlumno, idAsignatura);

        if (asignaturaActual.getEstado() != NO_CURSADA) {
            throw new EstadoIncorrectoException("La materia ya está cursada o aprobada");
        }

        // Cursamos la asignatura
        asignaturaActual.setEstado(CURSADA);
    }

    @Override
    public void aprobarAsignatura(long idAlumno, int nota, long idAsignatura)
            throws AlumnoNotFoundException, AsignaturaInexistenteException, NotaIncorrectaException,
            CorrelatividadesNoAprobadasException {

        // Buscamos la asignatura por su id
        Asignatura asignaturaActual = getAsignaturaPorId(idAlumno, idAsignatura);

        if (asignaturaActual.getEstado() != CURSADA) {
            throw new EstadoIncorrectoException("La materia debe estar CURSADA para poder aprobarse.");
        }
        if (nota < 6 || nota > 10  ){
            throw new NotaIncorrectaException("La nota debe estar entre 6 y 10 para poder aprobarse");
        }

        // Verificamos las correlativas en un método privado
        verificarCorrelativas(idAlumno, asignaturaActual);

        asignaturaActual.setEstado(APROBADA);
        asignaturaActual.setNota(nota);
    }

    @Override
    public void perderRegularidadAsignatura(long idAlumno, long idAsignatura)
            throws AsignaturaInexistenteException, AlumnoNotFoundException {

        // Buscamos la asignatura por su id
        Asignatura asignaturaActual = getAsignaturaPorId(idAlumno, idAsignatura);

        if (asignaturaActual.getEstado() == NO_CURSADA) {
            throw new EstadoIncorrectoException("La materia ya está no cursada");
        }

        // Perdemos la regularidad de la asignatura
        asignaturaActual.setEstado(NO_CURSADA);
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
    public Asignatura getAsignaturaPorId(long idAlumno, long idAsignatura) throws AlumnoNotFoundException,
            AsignaturaInexistenteException {

        // Buscamos al alumno
        Alumno alumno = findAlumno(idAlumno);

        // Retornamos el estado de la asignatura (si existe)
        return alumno.getAsignaturaAlumno(idAsignatura);
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

    // Métodos auxiliares ---------------------------------------------

    private void verificarExistenciaAlumno(long idAlumno) throws AlumnoNotFoundException {

        // Verificamos si el alumno existe en el repositorio
        if (!repositorioAlumnos.containsKey(idAlumno)) {
            throw new AlumnoNotFoundException("No se encontró ningún alumno con el id " + idAlumno);
        }
    }

    private void verificarCorrelativas(long idAlumno, Asignatura asignatura) throws AlumnoNotFoundException, CorrelatividadesNoAprobadasException {
        Alumno alumno = findAlumno(idAlumno);
        List<Integer> listaCorrelatividades = asignatura.getMateria().getCorrelatividades();

        // Verificamos si el alumno ha aprobado todas las correlativas requeridas
        for (Integer correlativaId : listaCorrelatividades) {
            if (!alumno.haAprobadoAsignatura(correlativaId)) {
                // Si no ha aprobado una correlativa, retornamos una exception
                throw new CorrelatividadesNoAprobadasException("No puede aprobar la asignatura puesto que aún no " +
                        "aprobó la asignatura con id " + correlativaId);
            }
        }
    }


    // ----------------------------------------------------------------
}
