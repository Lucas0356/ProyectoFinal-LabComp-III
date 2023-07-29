package ar.edu.utn.frbb.tup.business.impl;

import ar.edu.utn.frbb.tup.business.AlumnoService;
import ar.edu.utn.frbb.tup.business.AsignaturaService;
import ar.edu.utn.frbb.tup.business.exceptions.*;
import ar.edu.utn.frbb.tup.model.Alumno;
import ar.edu.utn.frbb.tup.model.Profesor;
import ar.edu.utn.frbb.tup.model.dto.AlumnoDto;
import ar.edu.utn.frbb.tup.model.dto.ProfesorDto;
import ar.edu.utn.frbb.tup.model.exception.CorrelatividadesNoAprobadasException;
import ar.edu.utn.frbb.tup.model.exception.EstadoIncorrectoException;
import ar.edu.utn.frbb.tup.persistence.AlumnoDao;
import ar.edu.utn.frbb.tup.persistence.exception.AlumnoNotFoundException;
import ar.edu.utn.frbb.tup.persistence.exception.ProfesorNotFoundException;
import ar.edu.utn.frbb.tup.persistence.impl.AlumnoDaoMemoryImpl;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class AlumnoServiceImpl implements AlumnoService {

    private static final AlumnoDao alumnoDao = new AlumnoDaoMemoryImpl();
    private static final AsignaturaService asignaturaService = new AsignaturaServiceImpl();

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
    public Alumno crearAlumno(AlumnoDto alumno) {
        // Validamos que los datos sean correctos
        validarNombreOApellido(alumno.getNombre(), "nombre", "validarNulo");
        validarNombreOApellido(alumno.getApellido(), "apellido", "validarNulo");
        validarDNI(String.valueOf(alumno.getDni()));

        Alumno a = new Alumno();

        a.setNombre(alumno.getNombre());
        a.setApellido(alumno.getApellido());
        a.setDni(alumno.getDni());

        alumnoDao.saveAlumno(a);
        return a;
    }

    @Override
    public Alumno buscarAlumno(String idString) throws AlumnoNotFoundException {
        // Verificar que el ID sea válido
        long id = validarId(idString);

        // Buscar al profesor por el ID

        return alumnoDao.findAlumno(id);
    }

    @Override
    public void eliminarAlumno(long idAlumno) {
    }

    @Override
    public Alumno modificarAlumno(String idAlumno, ProfesorDto alumno) {
        return null;
    }

    @Override
    public String borrarAlumno(String id) {
        return null;
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

    private long validarDNI(String dniString) throws DniInvalidoException, NumeroInvalidoException {
        long DniLong;

        // Verificar que el ID no esté vacío
        if (dniString == null || dniString.trim().isEmpty()) {
            throw new IdInvalidoException("El DNI no puede estar vacío.");
        }

        try {
            long dniLong = Long.parseLong(dniString); // Intentar convertir la cadena en un número long

            // Verificar que el DNI tenga exactamente 8 dígitos
            if (dniString.length() != 8) {
                throw new DniInvalidoException("El DNI debe contener exactamente 8 dígitos.");
            }

            // Verificar que el DNI sea un número válido (mayor que 0)
            if (dniLong <= 0) {
                throw new DniInvalidoException("El DNI no es válido, debe ser un número mayor a 0.");
            }

            return dniLong;
        } catch (NumberFormatException e) {
            throw new NumeroInvalidoException("El DNI no es válido, debe ser un número entero.");
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
