package ar.edu.utn.frbb.tup.model;

import java.util.List;

public class Carrera {

    // Atributos --------------------------------------------------------------

    private final String nombre;
    private int cantidadAnios;

    private List<Materia> materiasList;

    // ------------------------------------------------------------------------

    // Constructores ----------------------------------------------------------

    public Carrera(String nombre, int cantidadAnios) {
        this.nombre = nombre;
        this.cantidadAnios = cantidadAnios;
    }

    // ------------------------------------------------------------------------

    // Getters y Setters ------------------------------------------------------

    public String getNombre() {
        return nombre;
    }

    public int getCantidadAnios() {
        return cantidadAnios;
    }

    public void setCantidadAnios(int cantidadAnios) {
        this.cantidadAnios = cantidadAnios;
    }

    // ------------------------------------------------------------------------

    // Métodos relacionaos con materiasList -----------------------------------

    public void agregarMateria(Materia materia) {
        materiasList.add(materia);
    }

    public List<Materia> obtenerListaMaterias(){
        return this.materiasList;
    }

    // ------------------------------------------------------------------------
}
