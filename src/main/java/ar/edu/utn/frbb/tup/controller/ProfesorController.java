package ar.edu.utn.frbb.tup.controller;

import ar.edu.utn.frbb.tup.business.ProfesorService;
import ar.edu.utn.frbb.tup.model.Materia;
import ar.edu.utn.frbb.tup.model.Profesor;
import ar.edu.utn.frbb.tup.model.dto.ProfesorDto;
import ar.edu.utn.frbb.tup.persistence.exception.MateriaNotFoundException;
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

    @GetMapping("/{idProfesor}")
    public Profesor buscarProfesor(@PathVariable long idProfesor) throws ProfesorNotFoundException {
        return profesorService.buscarProfesor(idProfesor);
    }

    @PutMapping("/{idProfesor}")
    public Profesor modificarProfesor(@PathVariable long idProfesor, @RequestBody ProfesorDto profesorDto)
            throws ProfesorNotFoundException {
        return profesorService.modificarProfesor(idProfesor, profesorDto);
    }

    @DeleteMapping("/{idProfesor}")
    public String borrarProfesor(@PathVariable long idProfesor)
            throws ProfesorNotFoundException, MateriaNotFoundException {
        return profesorService.borrarProfesor(idProfesor);
    }

    // ----------------------------------------------------------------

    // Métodos varios -------------------------------------------------

    @GetMapping("/{idProfesor}/materias")
    public List<Materia> obtenerMateriasDictadasProfesor(@PathVariable long idProfesor)
            throws ProfesorNotFoundException{
        return profesorService.obtenerMateriasDictadasProfesor(idProfesor);
    }

    // ----------------------------------------------------------------

}
