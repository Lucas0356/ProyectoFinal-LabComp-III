package ar.edu.utn.frbb.tup.model;

import java.util.List;

public class Profesor {

    // Atributos --------------------------------------------------------------

    private final long id;
    private final String nombre;
    private final String apellido;
    private final String titulo;

    private List<Materia> materiasDictadas;

    // ------------------------------------------------------------------------

    // Constructores ----------------------------------------------------------

    public Profesor(String nombre, String apellido, String titulo) {
        this.apellido = apellido;
        this.nombre = nombre;
        this.titulo = titulo;
        this.id = generarID();
    }

    // ------------------------------------------------------------------------

    // Getters y Setters ------------------------------------------------------

    public long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getTitulo() {
        return titulo;
    }

    // ------------------------------------------------------------------------

    // Métodos relacionados con materias dictadas -----------------------------

    public void agregarMateriaDictada(Materia materia) {
        materiasDictadas.add(materia);
    }

    public List<Materia> obtenerListaMateriasDictadas(){
        return this.materiasDictadas;
    }

    // ------------------------------------------------------------------------

    // Métodos auxiliares -----------------------------------------------------

    private long generarID() {
        return 0;
    }

    // ------------------------------------------------------------------------
}
