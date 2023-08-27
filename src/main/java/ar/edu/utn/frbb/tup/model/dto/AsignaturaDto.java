package ar.edu.utn.frbb.tup.model.dto;

import ar.edu.utn.frbb.tup.model.EstadoAsignatura;

public class AsignaturaDto {

    // Atributos --------------------------------------------------------------

    private EstadoAsignatura estadoAsignatura;
    private int nota;

    // ------------------------------------------------------------------------

    // Getters y Setters ------------------------------------------------------

    public EstadoAsignatura getEstadoAsignatura() {
        return estadoAsignatura;
    }

    public void setEstadoAsignatura(EstadoAsignatura estadoAsignatura) {
        this.estadoAsignatura = estadoAsignatura;
    }

    public int getNota() {
        return nota;
    }

    public void setNota(int nota) {
        this.nota = nota;
    }


    // ------------------------------------------------------------------------

}
