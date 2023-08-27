package ar.edu.utn.frbb.tup.business.impl;

import ar.edu.utn.frbb.tup.business.MateriaService;
import ar.edu.utn.frbb.tup.business.ProfesorService;
import ar.edu.utn.frbb.tup.business.exceptions.*;
import ar.edu.utn.frbb.tup.model.Materia;
import ar.edu.utn.frbb.tup.model.Profesor;
import ar.edu.utn.frbb.tup.model.dto.MateriaDto;
import ar.edu.utn.frbb.tup.persistence.AlumnoDao;
import ar.edu.utn.frbb.tup.persistence.MateriaDao;
import ar.edu.utn.frbb.tup.persistence.exception.MateriaNotFoundException;
import ar.edu.utn.frbb.tup.persistence.exception.ProfesorNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MateriaServiceImpl implements MateriaService {

    // Dependencias -----------------------------------------------------------

    @Autowired
    private MateriaDao materiaDao;

    @Autowired
    private ProfesorService profesorService;

    // ------------------------------------------------------------------------

    // Constructor ------------------------------------------------------------

    public MateriaServiceImpl(MateriaDao materiaDao) {
        this.materiaDao = materiaDao;
    }

    // ------------------------------------------------------------------------

    // Métodos para operaciones CRUD de Materia -------------------------------

    @Override
    public Materia crearMateria(MateriaDto materiaDto) throws ProfesorNotFoundException {

        // Verificamos que los datos sean válidos
        validarNombreMateria(materiaDto.getNombre());
        validarAnio(materiaDto.getAnio());
        validarCuatrimestre(materiaDto.getCuatrimestre());

        // Creamos la materia vacía
        Materia materia = new Materia();

        // Asignamos los atributos a la materia
        materia.setNombre(materiaDto.getNombre());
        materia.setAnio(materiaDto.getAnio());
        materia.setCuatrimestre(materiaDto.getCuatrimestre());

        // Buscamos al profesor por su ID para asignarlo a la materia
        Profesor profesor = profesorService.buscarProfesor(materiaDto.getProfesorId());
        materia.setProfesor(profesor);

        // Asignamos las correlatividades a la materia
        materia.setCorrelatividades(materiaDto.getCorrelatividades());

        // Guardamos la nueva materia
        materiaDao.saveMateria(materia);

        return materia;
    }

    // ------------------------------------------------------------------------

    // Otros métodos ----------------------------------------------------------

    @Override
    public List<Materia> obtenerTodasLasMaterias() {

        return materiaDao.obtenerTodasLasMaterias();
    }

    @Override
    public Materia buscarMateria(int idMateria) throws MateriaNotFoundException {

        // Verificamos que el ID proporcionado sea válido
        validarIdMateria(idMateria);

        // Buscamos y retornamos la materia por su ID
        return materiaDao.findMateria(idMateria);
    }

    // ------------------------------------------------------------------------

    // Métodos auxiliares para validar datos ----------------------------------

    private void validarNombreMateria(String nombreMateria) throws NombreInvalidoException{

        // Verificamos que el nombre de la materia no esté vacío
        if (nombreMateria == null || nombreMateria.trim().isEmpty()) {
            throw new NombreInvalidoException("El nombre de la materia no puede estar vacío.");
        }

        // Verificamos que el nombre de la materia contenga solo letras y espacios
        if (!nombreMateria.matches("^[a-zA-Z0-9 .]+$")) {
            throw new NombreInvalidoException("El nombre de la materia no es válido.");
            }
        }

    private void validarAnio(int anio) throws AnioInvalidoException{

        // Verificamos que el año no sea menor que 0
        if (anio <= 0) {
            throw new AnioInvalidoException("El año no puede ser menor o igual que 0.");
        }

        // Verificar que el año sea menor o igual que 6
        if (anio >= 6) {
            throw new AnioInvalidoException("El año no puede ser mayor que 6.");
        }
    }

    private void validarCuatrimestre(int cuatrimestre) throws CuatrimestreInvalidoException {

        // Verificamos que el cuatrimestre sea un número válido (entre 1 y 2)
        if (cuatrimestre < 1 || cuatrimestre > 2) {
            throw new CuatrimestreInvalidoException("El cuatrimestre no es válido. Debe ser 1 o 2.");
        }
    }

    private void validarIdMateria(int idMateria) throws IdInvalidoException {

        // Verificamos que el id sea un número mayor a 0
        if (idMateria <= 0) {
            throw new IdInvalidoException("El ID no es válido, debe ser un número mayor a 0.");
        }
    }

    // ------------------------------------------------------------------------
}
