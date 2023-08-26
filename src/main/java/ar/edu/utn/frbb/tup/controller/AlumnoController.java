package ar.edu.utn.frbb.tup.controller;

import ar.edu.utn.frbb.tup.business.AlumnoService;
import ar.edu.utn.frbb.tup.business.MateriaService;
import ar.edu.utn.frbb.tup.model.Alumno;
import ar.edu.utn.frbb.tup.model.EstadoAsignatura;
import ar.edu.utn.frbb.tup.model.Materia;
import ar.edu.utn.frbb.tup.model.dto.AlumnoDto;
import ar.edu.utn.frbb.tup.model.dto.ProfesorDto;
import ar.edu.utn.frbb.tup.model.exception.AsignaturaInexistenteException;
import ar.edu.utn.frbb.tup.persistence.exception.AlumnoNotFoundException;
import ar.edu.utn.frbb.tup.persistence.exception.ProfesorNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/alumno")
public class AlumnoController {

    @Autowired
    private AlumnoService alumnoService;
    @Autowired
    private MateriaService materiaService;

    // Métodos para operaciones CRUD de Alumno ------------------------

    @PostMapping("")
    public Alumno crearAlumno(@RequestBody AlumnoDto alumnoDto) {
        List<Materia> materiasExistentes = materiaService.obtenerTodasLasMaterias();
        return alumnoService.crearAlumno(alumnoDto, materiasExistentes);
    }

    @GetMapping("/{id}")
    public Alumno buscarAlumno(@PathVariable String id) throws AlumnoNotFoundException {
        return alumnoService.buscarAlumno(id);
    }

    @PutMapping("/{id}")
    public Alumno modificarAlumno(@PathVariable Long id, @RequestBody AlumnoDto alumnoDto)
            throws AlumnoNotFoundException {
        return alumnoService.modificarAlumno(id, alumnoDto);
    }

    @DeleteMapping("/{id}")
    public String borrarAlumno(@PathVariable String id){
        return alumnoService.borrarAlumno(id);
    }

    // ----------------------------------------------------------------

    // Métodos para operaciones relacionadas con asignaturas ----------

    @GetMapping("/{idAlumno}/asignatura/{idAsignatura}")
    public EstadoAsignatura buscarEstadoAsignatura(
            @PathVariable String idAlumno,
            @PathVariable String idAsignatura) throws AlumnoNotFoundException, AsignaturaInexistenteException {
        return alumnoService.buscarEstadoAsignatura(idAlumno, idAsignatura);
    }

    // ----------------------------------------------------------------

}
