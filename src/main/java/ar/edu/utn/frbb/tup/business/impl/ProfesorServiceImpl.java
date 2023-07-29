package ar.edu.utn.frbb.tup.business.impl;

import ar.edu.utn.frbb.tup.business.ProfesorService;
import ar.edu.utn.frbb.tup.business.exceptions.ApellidoInvalidoException;
import ar.edu.utn.frbb.tup.business.exceptions.NombreInvalidoException;
import ar.edu.utn.frbb.tup.business.exceptions.TituloInvalidoException;
import ar.edu.utn.frbb.tup.model.Profesor;
import ar.edu.utn.frbb.tup.model.dto.ProfesorDto;
import ar.edu.utn.frbb.tup.persistence.ProfesorDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfesorServiceImpl implements ProfesorService {
    @Autowired
    private ProfesorDao profesorDao;

    // Constructor ------------------------------------------------------------

    public ProfesorServiceImpl(ProfesorDao profesorDao) {
        this.profesorDao = profesorDao;
    }

    // ------------------------------------------------------------------------

    // Métodos para operaciones CRUD de Profesor ------------------------------

    @Override
    public Profesor crearProfesor(ProfesorDto profesor) throws NombreInvalidoException,
            ApellidoInvalidoException {
        // Validamos que los datos sean correctos
        validarNombreOApellido(profesor.getNombre(), "nombre");
        validarNombreOApellido(profesor.getApellido(), "apellido");
        validarTitulo(profesor.getTitulo());

        Profesor p = new Profesor();
        p.setNombre(profesor.getNombre().trim());
        p.setApellido(profesor.getApellido().trim());
        p.setTitulo(profesor.getTitulo().trim());
        profesorDao.saveProfesor(p);

        return p;
    }


    @Override
    public Profesor buscarProfesor(long id) {
        return profesorDao.findProfesor(id);
    }

    // ------------------------------------------------------------------------

    // Métodos auxiliares para validar datos ----------------------------------

    private void validarNombreOApellido(String texto, String tipo) throws NombreInvalidoException,
            ApellidoInvalidoException {
        // Verificamos que el nombre/apellido no esté vacío
        if (texto == null || texto.trim().isEmpty()) {
            if (tipo.equals("nombre")) {
                throw new NombreInvalidoException("El nombre no puede estar vacío.");
            } else{
                throw new ApellidoInvalidoException("El apellido no puede estar vacío.");
            }
        }

        // Verificamos que el nombre/apellido contenga solo letras y espacios
        if (!texto.matches("^[a-zA-Z ]+$")) {
            if (tipo.equals("nombre")) {
                throw new NombreInvalidoException("El nombre no es válido.");
            } else{
                throw new ApellidoInvalidoException("El apellido no es válido.");
            }
        }
    }

    private void validarTitulo(String tituloMateria) throws TituloInvalidoException {
        // Verificar que el título del profesor no esté vacío
        if (tituloMateria == null || tituloMateria.trim().isEmpty()) {
            throw new TituloInvalidoException("El título del profesor no puede estar vacío.");
        }
    }

    // ------------------------------------------------------------------------
}
