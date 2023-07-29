package ar.edu.utn.frbb.tup.controller;

import ar.edu.utn.frbb.tup.business.ProfesorService;
import ar.edu.utn.frbb.tup.model.Materia;
import ar.edu.utn.frbb.tup.model.Profesor;
import ar.edu.utn.frbb.tup.model.dto.ProfesorDto;
import ar.edu.utn.frbb.tup.persistence.exception.ListaVaciaException;
import ar.edu.utn.frbb.tup.persistence.exception.ProfesorNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public Profesor buscarProfesor(@PathVariable String id) throws ProfesorNotFoundException {
        return profesorService.buscarProfesor(id);
    }

    @PutMapping("/{id}")
    public Profesor modificarProfesor(@PathVariable String id, @RequestBody ProfesorDto profesorDto)
            throws ProfesorNotFoundException {
        return profesorService.modificarProfesor(id, profesorDto);
    }

    @DeleteMapping("/{id}")
    public String borrarProfesor(@PathVariable String id) throws ProfesorNotFoundException {
        return profesorService.borrarProfesor(id);
    }

    // ----------------------------------------------------------------

    // Métodos varios -------------------------------------------------

    @GetMapping("/{id}/materias")
    public List<Materia> obtenerMateriasDictadasProfesor(@PathVariable String id) throws ProfesorNotFoundException,
            ListaVaciaException {
        return profesorService.obtenerMateriasDictadasProfesor(id);
    }

    // ----------------------------------------------------------------

}
