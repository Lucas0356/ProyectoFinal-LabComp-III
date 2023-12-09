// Importación de paquetes y clases necesarios
package ar.edu.utn.frbb.tup.controller;

import ar.edu.utn.frbb.tup.business.AlumnoService;
import ar.edu.utn.frbb.tup.business.MateriaService;
import ar.edu.utn.frbb.tup.model.Alumno;
import ar.edu.utn.frbb.tup.model.EstadoAsignatura;
import ar.edu.utn.frbb.tup.model.dto.AlumnoDto;
import ar.edu.utn.frbb.tup.model.dto.AsignaturaDto;
import ar.edu.utn.frbb.tup.model.exception.AsignaturaInexistenteException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// Extensión de Spring para ejecutar pruebas de integración
@SpringBootTest
public class AlumnoControllerTest {

    // Instancia del controlador que está siendo probada
    @InjectMocks
    AlumnoController alumnoController;

    // Servicio simulado con Mockito para gestionar Alumno
    @Mock
    AlumnoService alumnoService;

    // Servicio simulado con Mockito para gestionar Materia
    @Mock
    MateriaService materiaService;

    // Instancia de MockMvc utilizada para simular solicitudes HTTP
    MockMvc mockMvc;

    // Configuración previa a cada prueba
    @BeforeEach
    public void setUp() {
        // Configurar MockMvc con el controlador a probar
        this.mockMvc = MockMvcBuilders.standaloneSetup(alumnoController).build();
    }

    // Prueba del método buscarAlumno
    @Test
    public void buscarAlumnoTest() throws Exception {
        // Crear una instancia de Alumno para simular el resultado del servicio
        Alumno alumno = new Alumno();

        // Configurar el comportamiento del servicio de alumno al llamar a buscarAlumno
        Mockito.when(alumnoService.buscarAlumno(any(Long.class))).thenReturn(alumno);

        // Configurar el controlador para la prueba
        this.mockMvc = MockMvcBuilders.standaloneSetup(alumnoController).build();

        // Realizar una solicitud HTTP GET para buscar un alumno por su ID y esperar un estado de éxito (2xx)
        mockMvc.perform(MockMvcRequestBuilders.get("/alumno/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Verificar que la respuesta coincide con el alumno devuelto por el servicio
        assertEquals(alumno, alumnoController.buscarAlumno(1));
    }

    // Prueba del método borrarAlumno
    @Test
    public void borrarAlumnoTest() throws Exception {
        // Configurar el comportamiento del servicio de alumno al llamar a borrarAlumno
        Mockito.when(alumnoService.borrarAlumno(any(Long.class))).thenReturn("Alumno eliminado");

        // Configurar el controlador para la prueba
        this.mockMvc = MockMvcBuilders.standaloneSetup(alumnoController).build();

        // Realizar una solicitud HTTP DELETE para borrar un alumno por su ID y esperar un estado de éxito (2xx)
        mockMvc.perform(MockMvcRequestBuilders.delete("/alumno/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Verificar que la respuesta coincide con el mensaje esperado
        assertEquals("Alumno eliminado", alumnoController.borrarAlumno(1));
    }

    // Prueba del método buscarEstadoAsignatura
    @Test
    public void buscarEstadoAsignaturaTest() throws Exception, AsignaturaInexistenteException {
        // Crear una instancia de EstadoAsignatura para simular el resultado del servicio
        EstadoAsignatura estadoAsignatura = EstadoAsignatura.CURSADA;

        // Configurar el comportamiento del servicio de alumno al llamar a buscarEstadoAsignatura
        Mockito.when(alumnoService.buscarEstadoAsignatura(any(Long.class), any(Long.class))).thenReturn(estadoAsignatura);

        // Configurar el controlador para la prueba
        this.mockMvc = MockMvcBuilders.standaloneSetup(alumnoController).build();

        // Realizar una solicitud HTTP GET para buscar el estado de una asignatura para un alumno y esperar un estado de éxito (2xx)
        mockMvc.perform(MockMvcRequestBuilders.get("/alumno/1/asignatura/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Verificar que la respuesta coincide con el estado de asignatura devuelto por el servicio
        assertEquals(estadoAsignatura, alumnoController.buscarEstadoAsignatura(1, 2));
    }

    // Prueba del método modificarEstadoAsignatura
    @Test
    public void modificarEstadoAsignaturaTest() throws Exception, AsignaturaInexistenteException {
        // Configurar el comportamiento del servicio al llamar a modificarAsignatura
        Mockito.doNothing().when(alumnoService).modificarAsignatura(Mockito.anyLong(), Mockito.anyLong(), Mockito.any(AsignaturaDto.class));

        // Configurar el controlador para la prueba
        this.mockMvc = MockMvcBuilders.standaloneSetup(alumnoController).build();

        // Realizar una solicitud HTTP PUT para modificar el estado de una asignatura para un alumno y esperar un estado de éxito (2xx)
        mockMvc.perform(MockMvcRequestBuilders.put("/alumno/1/asignatura/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"estado\":\"APROBADA\"}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }

    // Prueba del método crearAlumno
    @Test
    public void crearAlumnoTest() throws Exception {
        // Configurar el comportamiento del servicio al llamar a crearAlumno
        Mockito.when(materiaService.obtenerTodasLasMaterias()).thenReturn(Collections.emptyList());
        Mockito.when(alumnoService.crearAlumno(Mockito.any(AlumnoDto.class), Mockito.any())).thenReturn(new Alumno());

        // Configurar el controlador para la prueba
        this.mockMvc = MockMvcBuilders.standaloneSetup(alumnoController).build();

        // Crear un objeto AlumnoDto para la solicitud
        AlumnoDto alumnoDto = new AlumnoDto();
        alumnoDto.setNombre("Lucas");
        alumnoDto.setApellido("Moltedo");
        alumnoDto.setDni(44881416);

        // Convertir el objeto AlumnoDto a formato JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String alumnoJson = objectMapper.writeValueAsString(alumnoDto);

        // Realizar una solicitud HTTP POST y esperar un estado de éxito (2xx)
        mockMvc.perform(MockMvcRequestBuilders.post("/alumno")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(alumnoJson)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }
}
