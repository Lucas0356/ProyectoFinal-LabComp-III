package ar.edu.utn.frbb.tup.business.impl;

import ar.edu.utn.frbb.tup.business.ProfesorService;
import ar.edu.utn.frbb.tup.business.exceptions.*;
import ar.edu.utn.frbb.tup.model.Profesor;
import ar.edu.utn.frbb.tup.model.dto.ProfesorDto;
import ar.edu.utn.frbb.tup.persistence.ProfesorDao;
import ar.edu.utn.frbb.tup.persistence.exception.ProfesorNotFoundException;
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
    public Profesor crearProfesor(ProfesorDto profesor) {
        // Validamos que los datos sean correctos
        validarNombreOApellido(profesor.getNombre(), "nombre", "validarNulo");
        validarNombreOApellido(profesor.getApellido(), "apellido", "validarNulo");
        validarTitulo(profesor.getTitulo());

        Profesor p = new Profesor();

        p.setNombre(profesor.getNombre().trim());
        p.setApellido(profesor.getApellido().trim());
        p.setTitulo(profesor.getTitulo().trim());
        profesorDao.saveProfesor(p);

        return p;
    }

    @Override
    public Profesor buscarProfesor(String idString) throws ProfesorNotFoundException {
        // Verificar que el ID sea válido
        long id = validarId(idString);

        // Buscar al profesor por el ID
        Profesor profesor = profesorDao.findProfesor(id);

        // Si no encuentra al profesor con el ID:
        if (profesor == null) {
            throw new ProfesorNotFoundException("No se encontró ningún profesor con el ID proporcionado.");
        }

        return profesor;
    }

    @Override
    public Profesor modificarProfesor(String idString, ProfesorDto nuevoProfesor)
            throws ProfesorNotFoundException {
        Profesor p = new Profesor(nuevoProfesor.getNombre(), nuevoProfesor.getApellido(),
                nuevoProfesor.getTitulo());

        // Validamos que los datos sean válidos (no nulos porque esos no se modifican)
        validarNombreOApellido(p.getNombre(), "nombre", "noValidarNulo");
        validarNombreOApellido(p.getApellido(), "apellido", "noValidarNulo");

        // Verificar que el ID sea válido
        long id = validarId(idString);

        return(profesorDao.updateProfesor(id, p));
    }

    @Override
    public String borrarProfesor(String idString) throws ProfesorNotFoundException {
        // Verificar que el ID sea válido
        long id = validarId(idString);

        // Borramos al profesor en su id
        profesorDao.deleteProfesor(id);

        return "El profesor con el id " + id + " ha sido eliminado correctamente.";
    }

    // ------------------------------------------------------------------------

    // Métodos auxiliares para validar datos ----------------------------------

    private void validarNombreOApellido(String texto, String tipo, String validarNulo) throws NombreInvalidoException,
            ApellidoInvalidoException {
        if (validarNulo.equals("validarNulo")){
            // Verificamos que el nombre/apellido no esté vacío
            if (texto == null || texto.trim().isEmpty()) {
                if (tipo.equals("nombre")) {
                    throw new NombreInvalidoException("El nombre no puede estar vacío.");
                } else{
                    throw new ApellidoInvalidoException("El apellido no puede estar vacío.");
                }
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

    private void validarTitulo(String tituloProfesor) throws TituloInvalidoException {
        // Verificar que el título del profesor no esté vacío
        if (tituloProfesor == null || tituloProfesor.trim().isEmpty()) {
            throw new TituloInvalidoException("El título del profesor no puede estar vacío.");
        }
    }

    private long validarId(String idString) throws IdInvalidoException, NumeroInvalidoException {
        long idLong;

        // Verificar que el ID no esté vacío
        if (idString == null || idString.trim().isEmpty()) {
            throw new IdInvalidoException("El ID no puede estar vacío.");
        }

        try {
            idLong = Long.parseLong(idString); // Intentar convertir la cadena en un número long
            if (idLong <= 0) {
                throw new IdInvalidoException("El ID no es válido, debe ser un número mayor a 0.");
            }
        } catch (NumberFormatException e) {
            throw new NumeroInvalidoException("El ID no es válido, debe ser un número entero.");
        }

        return idLong;
    }

    // ------------------------------------------------------------------------
}
