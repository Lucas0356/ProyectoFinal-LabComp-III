package ar.edu.utn.frbb.tup.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AlumnoController {

    @RequestMapping(value = "alumno")
    public String prueba(){
        return "acá se mostrarían los alumnos";
    }

}
