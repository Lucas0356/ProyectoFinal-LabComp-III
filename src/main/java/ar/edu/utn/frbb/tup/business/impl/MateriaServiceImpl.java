package ar.edu.utn.frbb.tup.business.impl;

import ar.edu.utn.frbb.tup.business.MateriaService;
import ar.edu.utn.frbb.tup.business.ProfesorService;
import ar.edu.utn.frbb.tup.business.exceptions.*;
import ar.edu.utn.frbb.tup.model.Materia;
import ar.edu.utn.frbb.tup.model.Profesor;
import ar.edu.utn.frbb.tup.model.dto.MateriaDto;
import ar.edu.utn.frbb.tup.persistence.MateriaDao;
import ar.edu.utn.frbb.tup.persistence.ProfesorDao;
import ar.edu.utn.frbb.tup.persistence.exception.MateriaNotFoundException;
import ar.edu.utn.frbb.tup.persistence.exception.ProfesorNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MateriaServiceImpl implements MateriaService {
    @Autowired
    private MateriaDao materiaDao;

    @Autowired
    private ProfesorService profesorService;

    // Constructor ------------------------------------------------------------

    public MateriaServiceImpl(MateriaDao materiaDao) {
        this.materiaDao = materiaDao;
    }

    // ------------------------------------------------------------------------

    // Métodos para operaciones CRUD de Materia -------------------------------

    @Override
    public Materia crearMateria(MateriaDto materia) throws ProfesorNotFoundException {
        // Validamos que los datos sean correctos
        validarNombreMateria(materia.getNombre(), "validarNulo");
        validarAnio(materia.getAnio());
        validarCuatrimestre(materia.getCuatrimestre());

        Materia m = new Materia();
        m.setNombre(materia.getNombre());
        m.setAnio(materia.getAnio());
        m.setCuatrimestre(materia.getCuatrimestre());

        // Buscamos al profesor por su ID para asignarlo a la materia
        Profesor profesor = profesorService.buscarProfesor(materia.getProfesorId());
        m.setProfesor(profesor);

        // Asignamos las correlatividades a la materia
        m.setCorrelatividades(materia.getCorrelatividades());

        materiaDao.saveMateria(m);
        return m;
    }

    // ------------------------------------------------------------------------

    // Otros métodos ----------------------------------------------------------

    @Override
    public List<Materia> getAllMaterias() {
        return null;
    }

    @Override
    public Materia buscarMateria(String idMateriaString) throws MateriaNotFoundException {
        int idMateria = validarIdMateria(idMateriaString);
        return materiaDao.findMateria(idMateria);
    }

    // ------------------------------------------------------------------------

    // Métodos auxiliares para validar datos ----------------------------------

    private void validarNombreMateria(String nombreMateria, String validarNulo) throws NombreInvalidoException{
        if (validarNulo.equals("validarNulo")){
            // Verificamos que el nombre de la materia no esté vacío
            if (nombreMateria == null || nombreMateria.trim().isEmpty()) {
                throw new NombreInvalidoException("El nombre de la materia no puede estar vacío.");
            }
        }

        // Verificamos que el nombre de la materia contenga solo letras y espacios
        if (!nombreMateria.matches("^[a-zA-Z0-9 .]+$")) {
            throw new NombreInvalidoException("El nombre de la materia no es válido.");
            }
        }

    private void validarAnio(int anio) throws AnioInvalidoException{
        // Verificar que el año no sea menor que 0
        if (anio <= 0) {
            throw new AnioInvalidoException("El año no puede ser menor o igual que 0.");
        }

        // Verificar que el año sea menor o igual que 6
        if (anio >= 6) {
            throw new AnioInvalidoException("El año no puede ser mayor que 6.");
        }
    }

    private void validarCuatrimestre(int cuatrimestre) throws CuatrimestreInvalidoException {
        // Verificar que el cuatrimestre sea un número válido (entre 1 y 2)
        if (cuatrimestre < 1 || cuatrimestre > 2) {
            throw new CuatrimestreInvalidoException("El cuatrimestre no es válido. Debe ser 1 o 2.");
        }
    }

    private int validarIdMateria(String idMateriaString) throws IdInvalidoException, NumeroInvalidoException {
        int idMateriaInt;

        // Verificar que el ID no esté vacío
        if (idMateriaString == null || idMateriaString.trim().isEmpty()) {
            throw new IdInvalidoException("El id no puede estar vacío.");
        }

        try {
            idMateriaInt = Integer.parseInt(idMateriaString); // Intentar convertir la cadena en un número int
            if (idMateriaInt <= 0) {
                throw new IdInvalidoException("El id no es válido, debe ser un número mayor a 0.");
            }
        } catch (NumberFormatException e) {
            throw new NumeroInvalidoException("El id no es válido, debe ser un número entero.");
        }

        return idMateriaInt;
    }

    // ------------------------------------------------------------------------
}
