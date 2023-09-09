package ar.edu.utn.frbb.tup.model;

import ar.edu.utn.frbb.tup.model.exception.AsignaturaInexistenteException;

import java.util.ArrayList;
import java.util.List;

import static ar.edu.utn.frbb.tup.model.EstadoAsignatura.CURSADA;
import static ar.edu.utn.frbb.tup.model.EstadoAsignatura.NO_CURSADA;

public class Alumno {

    // Atributos --------------------------------------------------------------

    private long id;
    private String nombre;
    private String apellido;
    private long dni;

    private List<Asignatura> asignaturas;

    // ------------------------------------------------------------------------

    // Constructores ----------------------------------------------------------

    public Alumno() {
    }

    public Alumno(String nombre, String apellido, long dni) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;

        asignaturas = new ArrayList<>();
    }

    // ------------------------------------------------------------------------

    // Getters y Setters ------------------------------------------------------

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public long getDni() {
        return dni;
    }

    public void setDni(long dni) {
        this.dni = dni;
    }

    public List<Asignatura> getAsignaturas() {
        return asignaturas;
    }

    public void setAsignaturas(List<Asignatura> asignaturas) {
        this.asignaturas = asignaturas;
    }

    // ------------------------------------------------------------------------

    // Métodos relacionados con asignaturas -----------------------------------

    public EstadoAsignatura getEstadoAsignaturaAlumno(long idAsignatura) throws AsignaturaInexistenteException {

        // Buscamos si existe la asignatura en la lista del alumno y devolvemos su estado
        for (Asignatura asignatura: asignaturas) {
            Materia materia = asignatura.getMateria();
            if ((materia.getMateriaId() == (idAsignatura))){
                return asignatura.getEstado();
            }
        }

        // Excepción asignatura inexistente
        throw new AsignaturaInexistenteException("No se encontró la asignatura.");
        }

    public Asignatura getAsignaturaAlumno(long idAsignatura) throws AsignaturaInexistenteException {

        // Buscamos si existe la asignatura en la lista del alumno y la devolvemos
        for (Asignatura asignatura: asignaturas) {
            Materia materia = asignatura.getMateria();
            if ((materia.getMateriaId() == (idAsignatura))){
                return asignatura;
            }
        }

        // Excepción asignatura inexistente
        throw new AsignaturaInexistenteException("No se encontró la asignatura.");
    }

    public void actualizarAsignatura(Asignatura asignaturaActualizada, EstadoAsignatura estadoAAsignar) throws AsignaturaInexistenteException {

        // Buscamos el id de la asignatura a actualizar
        long idAsignatura = asignaturaActualizada.getMateria().getMateriaId();

        // Buscamos la asignatura actual en la lista de asignaturas del alumno
        for (Asignatura asignatura : asignaturas) {
            if (asignatura.getMateria().getMateriaId() == idAsignatura) {

                // Actualiza el estado de la asignatura existente con la nueva información
                asignatura.setEstado(estadoAAsignar);

                return;
            }
        }

        // Si no se encuentra la asignatura, lanza una excepción
        throw new AsignaturaInexistenteException("La asignatura con ID " + idAsignatura + " no existe para este alumno.");
    }

    public void cursarAsignatura(Asignatura asignaturaActualizada)
            throws AsignaturaInexistenteException {

        // Buscamos el id de la asignatura a actualizar
        long idAsignatura = asignaturaActualizada.getMateria().getMateriaId();

        // Buscamos la asignatura actual en la lista de asignaturas del alumno
        for (Asignatura asignatura : asignaturas) {
            if (asignatura.getMateria().getMateriaId() == idAsignatura) {

                if (asignatura.getEstado() == NO_CURSADA) {
                    // Actualiza el estado de la asignatura existente con la nueva información
                    asignatura.setEstado(CURSADA);
                }

                return;
            }
        }

        // Si no se encuentra la asignatura, lanza una excepción
        throw new AsignaturaInexistenteException("La asignatura con ID " + idAsignatura + " no existe para este alumno.");
    }

    // ------------------------------------------------------------------------

}
