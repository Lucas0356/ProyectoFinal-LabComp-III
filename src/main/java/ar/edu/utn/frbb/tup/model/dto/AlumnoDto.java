package ar.edu.utn.frbb.tup.model.dto;

public class AlumnoDto {

    // Atributos --------------------------------------------------------------

    long id;
    String nombre;
    String apellido;
    long dni;

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
}
