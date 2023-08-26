package ar.edu.utn.frbb.tup.business.impl;

import ar.edu.utn.frbb.tup.business.AlumnoService;
import ar.edu.utn.frbb.tup.business.exceptions.*;
import ar.edu.utn.frbb.tup.model.Alumno;
import ar.edu.utn.frbb.tup.model.EstadoAsignatura;
import ar.edu.utn.frbb.tup.model.Materia;
import ar.edu.utn.frbb.tup.model.dto.AlumnoDto;
import ar.edu.utn.frbb.tup.model.exception.AsignaturaInexistenteException;
import ar.edu.utn.frbb.tup.model.exception.CorrelatividadesNoAprobadasException;
import ar.edu.utn.frbb.tup.model.exception.EstadoIncorrectoException;
import ar.edu.utn.frbb.tup.persistence.AlumnoDao;
import ar.edu.utn.frbb.tup.persistence.exception.AlumnoNotFoundException;
import ar.edu.utn.frbb.tup.persistence.impl.AlumnoDaoMemoryImpl;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AlumnoServiceImpl implements AlumnoService {

    private static final AlumnoDao alumnoDao = new AlumnoDaoMemoryImpl();

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

    @Override
    public void cursarAsignatura(long idAlumno, long idAsignatura) {
    }

    @Override
    public void perderRegularidadAsignatura(long idAlumno, long idAsignatura) throws EstadoIncorrectoException {
    }

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

    @Override
    public Alumno crearAlumno(AlumnoDto alumno, List<Materia> materiasExistentes) {
        // Validamos que los datos sean correctos
        validarNombreOApellido(alumno.getNombre(), "nombre", "validarNulo");
        validarNombreOApellido(alumno.getApellido(), "apellido", "validarNulo");
        validarDNI(alumno.getDni());

        Alumno a = new Alumno();

        a.setNombre(alumno.getNombre());
        a.setApellido(alumno.getApellido());
        a.setDni(alumno.getDni());

        alumnoDao.saveAlumno(a, materiasExistentes);
        return a;
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

        // Si los datos no son vacíos, verificamos que sean válidos.
        if (!alumnoModificado.getNombre().isEmpty()) {
            validarNombreOApellido(alumnoModificado.getNombre(), "nombre", "NoValidarNulo");
        }
        if (!alumnoModificado.getApellido().isEmpty()) {
            validarNombreOApellido(alumnoModificado.getApellido(), "apellido", "NoValidarNulo");
        }
        if (alumnoModificado.getDni() != 0) {
            validarDNI(alumnoModificado.getDni());
        }

        return(alumnoDao.updateAlumno(idAlumno , alumnoModificado));
    }

    @Override
    public String borrarAlumno(long idAlumno) throws AlumnoNotFoundException {
        // Obtenemos al alumno por el ID
        Alumno alumno = buscarAlumno(idAlumno);

        // Borramos al alumno
        alumnoDao.deleteAlumno(idAlumno);

        return "El alumno con el id " + idAlumno + " ha sido eliminado correctamente.";
    }

    @Override
    public void aprobarAsignatura(int materiaId, int nota, long dni) throws EstadoIncorrectoException, CorrelatividadesNoAprobadasException {
    }

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

    private long validarDNI(long dni) throws DniInvalidoException, NumeroInvalidoException {

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


    private long validarId(long id) throws IdInvalidoException, NumeroInvalidoException {

        if (id <= 0) {
            throw new IdInvalidoException("El ID no es válido, debe ser un número mayor a 0.");
        }

        return id;
    }

    // ------------------------------------------------------------------------
}
