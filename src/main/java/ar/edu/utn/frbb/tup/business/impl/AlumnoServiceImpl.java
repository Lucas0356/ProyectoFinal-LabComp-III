package ar.edu.utn.frbb.tup.business.impl;

import ar.edu.utn.frbb.tup.business.AlumnoService;
import ar.edu.utn.frbb.tup.business.exceptions.*;
import ar.edu.utn.frbb.tup.model.Alumno;
import ar.edu.utn.frbb.tup.model.EstadoAsignatura;
import ar.edu.utn.frbb.tup.model.Materia;
import ar.edu.utn.frbb.tup.model.Profesor;
import ar.edu.utn.frbb.tup.model.dto.AlumnoDto;
import ar.edu.utn.frbb.tup.model.exception.AsignaturaInexistenteException;
import ar.edu.utn.frbb.tup.model.exception.CorrelatividadesNoAprobadasException;
import ar.edu.utn.frbb.tup.model.exception.EstadoIncorrectoException;
import ar.edu.utn.frbb.tup.persistence.AlumnoDao;
import ar.edu.utn.frbb.tup.persistence.MateriaDao;
import ar.edu.utn.frbb.tup.persistence.ProfesorDao;
import ar.edu.utn.frbb.tup.persistence.exception.AlumnoNotFoundException;
import ar.edu.utn.frbb.tup.persistence.impl.AlumnoDaoMemoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AlumnoServiceImpl implements AlumnoService {

    // Dependencias -----------------------------------------------------------

    @Autowired
    private AlumnoDao alumnoDao;

    // ------------------------------------------------------------------------

    // Constructor ------------------------------------------------------------

    public AlumnoServiceImpl(AlumnoDao alumnoDao) {
        this.alumnoDao = alumnoDao;
    }

    // ------------------------------------------------------------------------

    // Métodos para operaciones CRUD de Alumno --------------------------------

    @Override
    public Alumno crearAlumno(AlumnoDto alumnoDto, List<Materia> materiasExistentes) {

        // Verificamos que los datos sean válidos
        validarNombreOApellido(alumnoDto.getNombre(), "nombre");
        validarNombreOApellido(alumnoDto.getApellido(), "apellido");
        validarDNI(alumnoDto.getDni());

        // Creamos al alumno
        Alumno alumno = new Alumno();

        // Asignamos los atributos al alumno
        alumno.setNombre(alumnoDto.getNombre());
        alumno.setApellido(alumnoDto.getApellido());
        alumno.setDni(alumnoDto.getDni());

        // Guardamos al nuevo alumno y le asignamos las materias existentes
        alumnoDao.saveAlumno(alumno, materiasExistentes);

        return alumno;
    }

    @Override
    public Alumno buscarAlumno(long idAlumno) throws AlumnoNotFoundException {

        // Verificamos que el ID proporcionado sea válido
        validarId(idAlumno);

        // Buscamos y retornamos al alumno por su ID
        return alumnoDao.findAlumno(idAlumno);
    }

    @Override
    public Alumno modificarAlumno(long idAlumno, AlumnoDto alumnoModificado) throws AlumnoNotFoundException {

        // Si los datos no son vacíos, verificamos que sean válidos
        if (!alumnoModificado.getNombre().isEmpty()) {
            validarNombreOApellido(alumnoModificado.getNombre(), "nombre");
        }
        if (!alumnoModificado.getApellido().isEmpty()) {
            validarNombreOApellido(alumnoModificado.getApellido(), "apellido");
        }
        if (alumnoModificado.getDni() != 0) {
            validarDNI(alumnoModificado.getDni());
        }

        // Nos conectamos con la otra capa para modificar al alumno y retornamos la modificación
        return(alumnoDao.updateAlumno(idAlumno , alumnoModificado));
    }

    @Override
    public String borrarAlumno(long idAlumno) throws AlumnoNotFoundException {

        // Buscamos al alumno por el ID, para verificar que exista
        buscarAlumno(idAlumno);

        // Borramos al alumno
        alumnoDao.deleteAlumno(idAlumno);

        return "El alumno con el id " + idAlumno + " ha sido eliminado correctamente.";
    }

    // ------------------------------------------------------------------------

    // Aprobar, cursar, perder regularidad asignatura -------------------------

    @Override
    public void aprobarAsignatura(int materiaId, int nota, long dni)
            throws EstadoIncorrectoException, CorrelatividadesNoAprobadasException {
    }

    @Override
    public void cursarAsignatura(long idAlumno, long idAsignatura) {
    }

    @Override
    public void perderRegularidadAsignatura(long idAlumno, long idAsignatura)
            throws EstadoIncorrectoException {
    }

    // ------------------------------------------------------------------------

    // Otros métodos relacionados con asignatura ------------------------------

    @Override
    public EstadoAsignatura buscarEstadoAsignatura(long idAlumno, long idAsignatura)
            throws AlumnoNotFoundException, AsignaturaInexistenteException {

        // Nos fijamos que exista el alumno
        buscarAlumno(idAlumno);

        // Validamos los IDS
        validarId(idAlumno);
        validarId(idAsignatura);

        // Retornamos el estado de la asignatura (si existe)
        return alumnoDao.getEstadoAsignaturaPorId(idAlumno, idAsignatura);
    }

    @Override
    public void actualizarAsignaturasAlumnos(Materia materia) {
        alumnoDao.actualizarAsignaturasAlumnos(materia);
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

    private long validarDNI(long dni) throws DniInvalidoException {

        // Convertimos el DNI en un string para hacer un .length()
        String dniString = String.valueOf(dni);

        // Verificamos que el DNI tenga exactamente 8 dígitos
        if (dniString.length() != 8) {
            throw new DniInvalidoException("El DNI debe contener exactamente 8 dígitos.");
        }

        // Verificamos que el DNI sea un número válido (mayor que 0)
        if (dni <= 0) {
            throw new DniInvalidoException("El DNI no es válido, debe ser un número mayor a 0.");
        }

        return dni;
    }


    private void validarId(long id) throws IdInvalidoException {

        // Verificamos que el id sea un número mayor a 0
        if (id <= 0) {
            throw new IdInvalidoException("El ID no es válido, debe ser un número mayor a 0.");
        }

    }

    // ------------------------------------------------------------------------
}



    /*
    @Override
    public void aprobarAsignatura(int materiaId, int nota, long dni) throws EstadoIncorrectoException, CorrelatividadesNoAprobadasException {
        Asignatura a = asignaturaService.getAsignatura(materiaId, dni);
        for (Materia m:
             a.getMateria().getCorrelatividades()) {
            Asignatura correlativa = asignaturaService.getAsignatura(m.getMateriaId(), dni);
            if (!EstadoAsignatura.APROBADA.equals(correlativa.getEstado())) {
                throw new CorrelatividadesNoAprobadasException("La materia " + m.getNombre() + " debe estar aprobada para aprobar " + a.getNombreAsignatura());
            }
        }
        a.aprobarAsignatura(nota);
        asignaturaService.actualizarAsignatura(a);
        Alumno alumno = alumnoDao.loadAlumno(dni);
        alumno.actualizarAsignatura(a);
        alumnoDao.saveAlumno(alumno);
    }
    */
