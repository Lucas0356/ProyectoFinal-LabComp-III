package ar.edu.utn.frbb.tup.business.impl;

import ar.edu.utn.frbb.tup.business.AsignaturaService;
import ar.edu.utn.frbb.tup.model.Asignatura;
import ar.edu.utn.frbb.tup.model.exception.AsignaturaInexistenteException;
import ar.edu.utn.frbb.tup.model.exception.CorrelatividadesNoAprobadasException;
import ar.edu.utn.frbb.tup.persistence.exception.NotaIncorrectaException;
import ar.edu.utn.frbb.tup.persistence.AlumnoDao;
import ar.edu.utn.frbb.tup.persistence.exception.AlumnoNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AsignaturaServiceImpl implements AsignaturaService {

    // Dependencias -----------------------------------------------------------

    @Autowired
    private AlumnoDao alumnoDao;

    // ------------------------------------------------------------------------

    @Override
    public void perderRegularidadAsignatura(long idAlumno, long idAsignatura)
            throws AsignaturaInexistenteException, AlumnoNotFoundException {

        // Perdemos regularidad de la materia
        alumnoDao.perderRegularidadAsignatura(idAlumno, idAsignatura);
    }

    @Override
    public void cursarAsignatura(long idAlumno, long idAsignatura)
            throws AsignaturaInexistenteException, AlumnoNotFoundException {

        // Cursamos la materia
        alumnoDao.cursarAsignatura(idAlumno, idAsignatura);
    }

    @Override
    public void aprobarAsignatura(long idAlumno, int nota, long idAsignatura)
            throws AsignaturaInexistenteException, AlumnoNotFoundException, NotaIncorrectaException, CorrelatividadesNoAprobadasException {

        // Aprobamos la materia
        alumnoDao.aprobarAsignatura(idAlumno, nota, idAsignatura);
    }

    @Override
    public Asignatura buscarAsignatura(long idAsignatura, long idAlumno) throws AsignaturaInexistenteException,
            AlumnoNotFoundException {
        return alumnoDao.getAsignaturaPorId(idAlumno, idAsignatura);
    }
}
