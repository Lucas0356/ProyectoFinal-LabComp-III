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

    public void setApellido(String apellido) {
        this.apellido = apellido;
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

    public boolean haAprobadoAsignatura(Integer correlativaId) {
        for (Asignatura asignatura : asignaturas) {
            if (asignatura.getMateria().getMateriaId() == correlativaId &&
                    asignatura.getEstado() == EstadoAsignatura.APROBADA) {
                return true;
            }
        }
        return false;
    }


    // ------------------------------------------------------------------------

}
