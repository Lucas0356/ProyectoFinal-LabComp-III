package ar.edu.utn.frbb.tup.model;

import java.util.ArrayList;
import java.util.List;

public class Profesor {

    // Atributos --------------------------------------------------------------

    private long id;
    private String nombre;
    private String apellido;
    private String titulo;

    private List<Integer> materiasDictadas;

    // ------------------------------------------------------------------------

    // Constructores ----------------------------------------------------------

    public Profesor() {
        this.materiasDictadas = new ArrayList<>();
    }

    public Profesor(String nombre, String apellido, String titulo) {
        this.apellido = apellido;
        this.nombre = nombre;
        this.titulo = titulo;

        this.materiasDictadas = new ArrayList<>();
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

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    // ------------------------------------------------------------------------

    // Métodos relacionados con materias dictadas -----------------------------

    public void agregarMateriaDictada(Materia materia) {
        if (materia != null) {
            materiasDictadas.add(materia.getMateriaId());
        }
    }

    public List<Integer> obtenerListaMateriasDictadas(){
        return this.materiasDictadas;
    }

    // ------------------------------------------------------------------------

}
