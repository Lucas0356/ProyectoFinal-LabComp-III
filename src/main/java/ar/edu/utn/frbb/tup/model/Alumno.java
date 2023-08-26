package ar.edu.utn.frbb.tup.model;

import ar.edu.utn.frbb.tup.model.exception.AsignaturaInexistenteException;
import ar.edu.utn.frbb.tup.model.exception.CorrelatividadException;

import java.util.ArrayList;
import java.util.List;

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

    public void agregarAsignatura(List<Asignatura> asignaturas) {
        this.asignaturas = asignaturas;
    }

    /*
    public void aprobarAsignatura(Materia materia, int nota) throws EstadoIncorrectoException, CorrelatividadException, AsignaturaInexistenteException {
        Asignatura asignaturaAAprobar = getAsignaturaAAprobar(materia);

        for (Materia correlativa :
                materia.getCorrelatividades()) {
            chequearCorrelatividad(correlativa);
        }

        asignaturaAAprobar.aprobarAsignatura(nota);
    }
    */

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

    private void chequearCorrelatividad(Materia correlativa) throws CorrelatividadException {
        for (Asignatura a:
                asignaturas) {
            if (correlativa.getNombre().equals(a.getNombreAsignatura())) {
                if (!EstadoAsignatura.APROBADA.equals(a.getEstado())) {
                    throw new CorrelatividadException("La asignatura " + a.getNombreAsignatura() + " no está aprobada");
                }
            }
        }
    }

    private Asignatura getAsignaturaAAprobar(Materia materia) throws AsignaturaInexistenteException {

        for (Asignatura a: asignaturas) {
            if (materia.getNombre().equals(a.getNombreAsignatura())) {
                return a;
            }
        }

        throw new AsignaturaInexistenteException("No se encontró la materia.");
    }

    public List<Asignatura> obtenerListaAsignaturas(){
        return this.asignaturas;
    }

    public boolean puedeAprobar(Asignatura asignatura) {
        return true;
    }

    public void actualizarAsignatura(Asignatura a) {
    }

    // ------------------------------------------------------------------------

}
