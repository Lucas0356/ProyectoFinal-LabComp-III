package ar.edu.utn.frbb.tup.model;

import java.util.List;

public class Profesor {

    // Atributos --------------------------------------------------------------

    private long id;
    private String nombre;
    private String apellido;
    private String titulo;

    private List<Materia> materiasDictadas;

    // ------------------------------------------------------------------------

    // Constructores ----------------------------------------------------------

    public Profesor() {
    }

    public Profesor(String nombre, String apellido, String titulo) {
        this.apellido = apellido;
        this.nombre = nombre;
        this.titulo = titulo;
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
