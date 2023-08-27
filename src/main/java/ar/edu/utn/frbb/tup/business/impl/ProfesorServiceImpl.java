package ar.edu.utn.frbb.tup.business.impl;

import ar.edu.utn.frbb.tup.business.ProfesorService;
import ar.edu.utn.frbb.tup.business.exceptions.*;
import ar.edu.utn.frbb.tup.model.Materia;
import ar.edu.utn.frbb.tup.model.Profesor;
import ar.edu.utn.frbb.tup.model.dto.ProfesorDto;
import ar.edu.utn.frbb.tup.persistence.MateriaDao;
import ar.edu.utn.frbb.tup.persistence.ProfesorDao;
import ar.edu.utn.frbb.tup.persistence.exception.MateriaNotFoundException;
import ar.edu.utn.frbb.tup.persistence.exception.ProfesorNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class ProfesorServiceImpl implements ProfesorService {

    // Dependencias -----------------------------------------------------------

    @Autowired
    private ProfesorDao profesorDao;

    @Autowired
    private MateriaDao materiaDao;

    // ------------------------------------------------------------------------

    // Constructor ------------------------------------------------------------

    public ProfesorServiceImpl(ProfesorDao profesorDao) {
        this.profesorDao = profesorDao;
    }

    // ------------------------------------------------------------------------

    // Métodos para operaciones CRUD de Profesor ------------------------------

    @Override
    public Profesor crearProfesor(ProfesorDto profesorDto) {

        // Verificamos que los datos sean válidos
        validarNombreOApellido(profesorDto.getNombre(), "nombre");
        validarNombreOApellido(profesorDto.getApellido(), "apellido");
        validarTitulo(profesorDto.getTitulo());

        // Creamos al profesor vacío
        Profesor profesor = new Profesor();

        // Asignamos los atributos al profesor
        profesor.setNombre(profesorDto.getNombre());
        profesor.setApellido(profesorDto.getApellido());
        profesor.setTitulo(profesorDto.getTitulo());

        // Guardamos al nuevo profesor
        profesorDao.saveProfesor(profesor);

        return profesor;
    }

    @Override
    public Profesor buscarProfesor(long idProfesor) throws ProfesorNotFoundException {

        // Verificamos que el ID proporcionado sea válido
        validarId(idProfesor);

        // Buscamos y retornamos al profesor por su ID
        return profesorDao.findProfesor(idProfesor);
    }

    @Override
    public Profesor modificarProfesor(long idProfesor, ProfesorDto profesorModificado)
            throws ProfesorNotFoundException {

        // Si los datos no son nulos ni vacíos, verificamos que sean válidos
        if (profesorModificado.getNombre() != null && !profesorModificado.getNombre().isEmpty()) {
            validarNombreOApellido(profesorModificado.getNombre(), "nombre");
        }
        if (profesorModificado.getApellido() != null && !profesorModificado.getApellido().isEmpty()) {
            validarNombreOApellido(profesorModificado.getApellido(), "apellido");
        }
        if (profesorModificado.getTitulo() != null && !profesorModificado.getTitulo().isEmpty()) {
            validarTitulo(profesorModificado.getTitulo());
        }

        // Nos conectamos con la otra capa para modificar al profesor y retornamos la modificación
        return(profesorDao.updateProfesor(idProfesor, profesorModificado));
    }

    @Override
    public String borrarProfesor(long idProfesor) throws ProfesorNotFoundException, MateriaNotFoundException {

        // Verificamos que el ID sea válido
        validarId(idProfesor);

        // Buscamos al profesor por el ID
        Profesor profesor = profesorDao.findProfesor(idProfesor);

        // Guardamos la lista de IDs de las materias dictadas por el profesor
        List<Integer> materiasDictadasIDs = profesor.obtenerListaMateriasDictadas();

        // Eliminamos cada materia dictada por el profesor
        for (int materiaID : materiasDictadasIDs) {
            materiaDao.deleteMateria(materiaID);
        }

        // Borramos al profesor
        profesorDao.deleteProfesor(idProfesor);

        return "El profesor con el id " + idProfesor + " ha sido eliminado correctamente," +
                " junto con sus materias dictadas.";
    }

    // ------------------------------------------------------------------------

    // Métodos relacionados con las materias dictadas -------------------------

    @Override
    public List<Materia> obtenerMateriasDictadasProfesor(long idProfesor) throws ProfesorNotFoundException{

        // Verificamos que el ID proporcionado sea válido
        validarId(idProfesor);

        // Buscamos al profesor por el ID
        Profesor profesor = profesorDao.findProfesor(idProfesor);

        // Guardamos una lista con los ID de todas las materias dictadas por el profesor
        List<Integer> materiasDictadasIDs = profesor.obtenerListaMateriasDictadas();

        // Creamos una lista para almacenar las materias
        List<Materia> materiasDictadas = new ArrayList<>();

        // Buscamos cada materia por su ID y la agregamos a la lista
        for (Integer idMateria : materiasDictadasIDs) {
            try {
                Materia materia = materiaDao.findMateria(idMateria);
                materiasDictadas.add(materia);
            } catch (MateriaNotFoundException e) {
                // Si la materia no se encuentra en el repositorio, simplemente la omitimos
            }
        }

        // Ordenar las materias alfabéticamente por su nombre
        materiasDictadas.sort(new Comparator<>() {
            @Override
            public int compare(Materia materia1, Materia materia2) {
                return materia1.getNombre().compareToIgnoreCase(materia2.getNombre());
            }
        });

        return materiasDictadas;
    }

    // ------------------------------------------------------------------------

    // Métodos auxiliares para validar datos ----------------------------------

    private void validarNombreOApellido(String texto, String tipo) throws NombreInvalidoException,
            ApellidoInvalidoException {

        // Verificamos que el nombre/apellido no esté vacío
        if (texto == null || texto.isEmpty()) {
            if (tipo.equals("nombre")) {
                throw new NombreInvalidoException("El nombre no puede estar vacío.");
            } else {
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

    private void validarTitulo(String tituloProfesor) throws TituloInvalidoException {

        // Verificamos que el título del profesor no esté vacío
        if (tituloProfesor == null || tituloProfesor.trim().isEmpty()) {
            throw new TituloInvalidoException("El título del profesor no puede estar vacío.");
        }

        // Verificamos que el título del profesor al menos tenga una letra
        if (!tituloProfesor.matches(".*[a-zA-Z].*")) {
            throw new TituloInvalidoException("El título del profersor debe contener al menos una letra.");
        }

    }

    private void validarId(long id) throws IdInvalidoException {

        // Verificamos que el id sea un número mayor a 0
        if (id <= 0) {
            throw new IdInvalidoException("El ID no es válido, debe ser un número mayor a 0.");
        }

    }

    // ------------------------------------------------------------------------
}
