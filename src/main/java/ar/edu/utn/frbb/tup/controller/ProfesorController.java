package ar.edu.utn.frbb.tup.controller;

import ar.edu.utn.frbb.tup.business.ProfesorService;
import ar.edu.utn.frbb.tup.model.Profesor;
import ar.edu.utn.frbb.tup.model.dto.ProfesorDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("profesor")
public class ProfesorController {

    @Autowired
    private ProfesorService profesorService;

    // Métodos para operaciones CRUD de Profesor ----------------------

    @PostMapping("")
    public Profesor crearProfesor(@RequestBody ProfesorDto profesorDto) {
        return profesorService.crearProfesor(profesorDto);
    }

    @GetMapping("/{id}")
    public Profesor buscarProfesor(@PathVariable long id) {
        return profesorService.buscarProfesor(id);
    }

    // ----------------------------------------------------------------

    // Métodos varios -------------------------------------------------

}
