package ar.edu.utn.frbb.tup.controller;

import ar.edu.utn.frbb.tup.business.AlumnoService;
import ar.edu.utn.frbb.tup.model.Alumno;
import ar.edu.utn.frbb.tup.model.dto.AlumnoDto;
import ar.edu.utn.frbb.tup.model.dto.ProfesorDto;
import ar.edu.utn.frbb.tup.persistence.exception.AlumnoNotFoundException;
import ar.edu.utn.frbb.tup.persistence.exception.ProfesorNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/alumno")
public class AlumnoController {

    @Autowired
    private AlumnoService alumnoService;

    // Métodos para operaciones CRUD de Alumno ------------------------

    @PostMapping("")
    public Alumno crearAlumno(@RequestBody AlumnoDto alumnoDto) {
        return alumnoService.crearAlumno(alumnoDto);
    }

    @GetMapping("/{id}")
    public Alumno buscarAlumno(@PathVariable String id) throws AlumnoNotFoundException {
        return alumnoService.buscarAlumno(id);
    }

    @PutMapping("/{id}")
    public Alumno modificarProfesor(@PathVariable String id, @RequestBody ProfesorDto profesorDto)
            throws ProfesorNotFoundException {
        return alumnoService.modificarAlumno(id, profesorDto);
    }

    @DeleteMapping("/{id}")
    public String borrarAlumno(@PathVariable String id){
        return alumnoService.borrarAlumno(id);
    }

    // ----------------------------------------------------------------
}
