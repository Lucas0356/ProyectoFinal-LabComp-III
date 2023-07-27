package ar.edu.utn.frbb.tup.model;

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

    // ------------------------------------------------------------------------

    // Métodos relacionados con asignaturas -----------------------------------

    public void agregarAsignatura(Asignatura asignatura){
        this.asignaturas.add(asignatura);
    }

    public List<Asignatura> obtenerListaAsignaturas(){
        return this.asignaturas;
    }

    public void aprobarAsignatura() {
    }

    private void chequearCorrelatividad() {
    }

    public boolean puedeAprobar(Asignatura asignatura) {
        return true;
    }

    public void actualizarAsignatura() {
    }

    // ------------------------------------------------------------------------

}
