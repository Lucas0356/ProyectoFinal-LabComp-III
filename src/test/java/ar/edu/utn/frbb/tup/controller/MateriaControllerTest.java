// Importación de paquetes y clases necesarios
package ar.edu.utn.frbb.tup.controller;

import ar.edu.utn.frbb.tup.business.AlumnoService;
import ar.edu.utn.frbb.tup.business.MateriaService;
import ar.edu.utn.frbb.tup.model.Materia;
import ar.edu.utn.frbb.tup.model.dto.MateriaDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// Extensión de Spring para ejecutar pruebas de integración
@SpringBootTest
public class MateriaControllerTest {

    // Instancia del controlador que está siendo probada
    @InjectMocks
    MateriaController materiaController;

    // Servicio simulado con Mockito para gestionar Materia
    @Mock
    MateriaService materiaService;

    // Servicio simulado con Mockito para gestionar Alumno
    @Mock
    AlumnoService alumnoService;

    // Instancia de MockMvc utilizada para simular solicitudes HTTP
    MockMvc mockMvc;

    // ObjectMapper para convertir objetos a JSON y viceversa
    private static final ObjectMapper mapper = new ObjectMapper();

    // Configuración previa a cada prueba
    @BeforeEach
    public void setUp() {
        // Configurar MockMvc con el controlador a probar
        this.mockMvc = MockMvcBuilders.standaloneSetup(materiaController).build();
    }

    // Prueba del método crearMateria
    @Test
    public void crearMateriaTest() throws Exception {
        // Configurar el comportamiento del servicio de materia al llamar a crearMateria
        // Se especifica que, al recibir cualquier objeto MateriaDto como argumento,
        // debe devolver una nueva instancia de Materia.
        Mockito.when(materiaService.crearMateria(any(MateriaDto.class))).thenReturn(new Materia());

        // Configura el comportamiento simulado para el servicio de Alumno
        // Simula que no se realiza ninguna acción al llamar a actualizarAsignaturasAlumnos en el servicio de Alumno.
        Mockito.doNothing().when(alumnoService).actualizarAsignaturasAlumnos(any(Materia.class));

        // Crear un objeto MateriaDto para la solicitud
        MateriaDto materiaDto = new MateriaDto();
        materiaDto.setAnio(2);
        materiaDto.setCuatrimestre(1);
        materiaDto.setNombre("Laboratorio III");
        materiaDto.setProfesorId(1);

        // Realizar una solicitud HTTP POST y esperar un estado de éxito (2xx)
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/materia")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(materiaDto))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        // Verificar que la respuesta coincide con una nueva instancia de Materia
        assertEquals(new Materia(), mapper.readValue(result.getResponse().getContentAsString(), Materia.class));

        // Verifica que el método actualizarAsignaturasAlumnos fue llamado
        // Esto debido a que cuando agregamos una materia, también se la agregamos a todos los alumnos existentes
        Mockito.verify(alumnoService).actualizarAsignaturasAlumnos(any(Materia.class));
    }

    // Prueba del método crearMateria cuando se realiza una solicitud con formato incorrecto
    @Test
    public void crearMateriaBadRequestTest() throws Exception {
        // Configurar el comportamiento del servicio de materia al llamar a crearMateria
        Mockito.when(materiaService.crearMateria(any(MateriaDto.class))).thenReturn(new Materia());

        // Simula una solicitud con formato incorrecto al enviar el año y cuatrimestre
        // como cadenas de texto en lugar de valores numéricos. Esperando un estado de error (bad request)
        mockMvc.perform(MockMvcRequestBuilders.post("/materia")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"nombre\" : \"Laboratorio III\",\n" +
                                "    \"anio\" : \"segundo\", \n" +
                                "    \"cuatrimestre\" : primero,\n" +
                                "    \"profesorId\" : 1,\n"+
                                "    \"correlatividades\": []"+
                                "}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    // Prueba del método buscarMateria
    @Test
    public void buscarMateriaTest() throws Exception {
        // Crear una instancia de Materia para simular el resultado del servicio
        Materia materia = new Materia();

        // Configurar el comportamiento del servicio de materia al llamar a buscarMateria
        Mockito.when(materiaService.buscarMateria(any(Integer.class))).thenReturn(materia);

        // Configurar el controlador para la prueba
        this.mockMvc = MockMvcBuilders.standaloneSetup(materiaController).build();

        // Realizar una solicitud HTTP GET para buscar una materia por su ID y esperar un estado de éxito (2xx)
        mockMvc.perform(MockMvcRequestBuilders.get("/materia/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Verificar que la respuesta coincide con la materia devuelta por el servicio
        assertEquals(materia, materiaController.buscarMateria(1));
    }
}
