package ar.edu.utn.frbb.tup.controller;

import ar.edu.utn.frbb.tup.business.AlumnoService;
import ar.edu.utn.frbb.tup.business.MateriaService;
import ar.edu.utn.frbb.tup.model.Materia;
import ar.edu.utn.frbb.tup.model.dto.MateriaDto;
import ar.edu.utn.frbb.tup.persistence.exception.MateriaNotFoundException;
import ar.edu.utn.frbb.tup.persistence.exception.ProfesorNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("materia")
public class MateriaController {

    @Autowired
    private MateriaService materiaService;
    @Autowired
    private AlumnoService alumnoService;

    // Métodos para operaciones CRUD de Materia -----------------------

    @PostMapping("")
    public Materia crearMateria(@RequestBody MateriaDto materiaDto) throws ProfesorNotFoundException {
        Materia materia = materiaService.crearMateria(materiaDto);
        alumnoService.actualizarAsignaturasAlumnos(materia);
        return materia;
    }

    @GetMapping("/{idMateria}")
    public Materia buscarMateria(@PathVariable int idMateria) throws MateriaNotFoundException {
        return materiaService.buscarMateria(idMateria);
    }

    // -------------------------------------------------------------------------

}
