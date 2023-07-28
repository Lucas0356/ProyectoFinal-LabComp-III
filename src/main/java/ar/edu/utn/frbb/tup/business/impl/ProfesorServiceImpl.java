package ar.edu.utn.frbb.tup.business.impl;

import ar.edu.utn.frbb.tup.business.ProfesorService;
import ar.edu.utn.frbb.tup.model.Profesor;
import ar.edu.utn.frbb.tup.model.dto.ProfesorDto;
import ar.edu.utn.frbb.tup.persistence.ProfesorDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfesorServiceImpl implements ProfesorService {
    @Autowired
    private ProfesorDao profesorDao;

    public ProfesorServiceImpl(ProfesorDao profesorDao) {
        this.profesorDao = profesorDao;
    }

    @Override
    public Profesor crearProfesor(ProfesorDto profesor) throws IllegalArgumentException{
        Profesor p = new Profesor();
        p.setNombre(profesor.getNombre());
        p.setApellido(profesor.getApellido());
        p.setTitulo(profesor.getTitulo());
        profesorDao.saveProfesor(p);
        if (p.getNombre().contains("x")) { // Para pruebas
            throw new IllegalArgumentException();
        }
        return p;
    }

    @Override
    public Profesor buscarProfesor(long id) {
        return profesorDao.findProfesor(id);
    }
}
