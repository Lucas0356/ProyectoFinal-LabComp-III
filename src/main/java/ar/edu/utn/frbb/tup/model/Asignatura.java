package ar.edu.utn.frbb.tup.model;


import ar.edu.utn.frbb.tup.model.exception.EstadoIncorrectoException;

import java.util.Optional;

public class Asignatura {

    // Atributos --------------------------------------------------------------

    private Materia materia;
    private EstadoAsignatura estado;
    private Integer nota;

    // ------------------------------------------------------------------------

    // Constructores ----------------------------------------------------------

    public Asignatura() {
    }

    public Asignatura(Materia materia) {
        this.materia = materia;
        this.estado = EstadoAsignatura.NO_CURSADA;
    }

    // ------------------------------------------------------------------------

    // Getters y Setters ------------------------------------------------------

    public Materia getMateria() {
        return materia;
    }

    public void setMateria(Materia materia) {
        this.materia = materia;
    }

    public String getNombreAsignatura(){
        return this.materia.getNombre();
    }

    public EstadoAsignatura getEstado() {
        return estado;
    }

    public void setEstado(EstadoAsignatura estado) {
        this.estado = estado;
    }

    public Optional<Integer> getNota() {
        return Optional.ofNullable(nota);
    }

    public void setNota(int nota) {
        this.nota = nota;
    }

    // ------------------------------------------------------------------------

    // Métodos para cursar y aprobar asignatura ----------------------------------

    public void cursarAsignatura(){
        this.estado = EstadoAsignatura.CURSADA;
    }

    public void aprobarAsignatura(int nota) throws EstadoIncorrectoException {
        if (!this.estado.equals(EstadoAsignatura.CURSADA)) {
            throw new EstadoIncorrectoException("La materia debe estar cursada.");
        }
        if (nota>=4) {
            this.estado = EstadoAsignatura.APROBADA;
            this.nota = nota;
        }
    }

    // ------------------------------------------------------------------------
}
