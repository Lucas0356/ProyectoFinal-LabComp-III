package ar.edu.utn.frbb.tup.model.dto;

public class MateriaDto {

    // Atributos --------------------------------------------------------------

    private String nombre;
    private int anio;
    private int cuatrimestre;
    private long profesorId;

    // ------------------------------------------------------------------------

    // Getters y Setters ------------------------------------------------------

    public long getProfesorId() {
        return profesorId;
    }

    public void setProfesorId(long profesorId) {
        this.profesorId = profesorId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public int getCuatrimestre() {
        return cuatrimestre;
    }

    public void setCuatrimestre(int cuatrimestre) {
        this.cuatrimestre = cuatrimestre;
    }

    // ------------------------------------------------------------------------

}
