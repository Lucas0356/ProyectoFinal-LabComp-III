package ar.edu.utn.frbb.tup.controller;

import ar.edu.utn.frbb.tup.business.ProfesorService;
import ar.edu.utn.frbb.tup.model.Profesor;
import ar.edu.utn.frbb.tup.model.dto.ProfesorDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
public class ProfesorControllerTest {

    @InjectMocks
    ProfesorController profesorController;

    @Mock
    ProfesorService profesorService;

    MockMvc mockMvc;

    // Prueba del método crearProfesor
    @Test
    public void crearProfesorTest() throws Exception {
        // Configurar el comportamiento del servicio al llamar a crearProfesor
        Mockito.when(profesorService.crearProfesor(Mockito.any(ProfesorDto.class))).thenReturn(new Profesor());

        // Configurar el controlador para la prueba
        this.mockMvc = MockMvcBuilders.standaloneSetup(profesorController).build();

        // Realizar una solicitud HTTP POST para crear un profesor y esperar un estado de éxito (2xx)
        mockMvc.perform(MockMvcRequestBuilders.post("/profesor")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nombre\":\"Juan\",\"apellido\":\"Perez\",\"dni\":123456}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }

    // Prueba del método buscarProfesor
    @Test
    public void buscarProfesorTest() throws Exception {
        // Configurar el comportamiento del servicio al llamar a buscarProfesor
        Mockito.when(profesorService.buscarProfesor(Mockito.anyLong())).thenReturn(new Profesor());

        // Configurar el controlador para la prueba
        this.mockMvc = MockMvcBuilders.standaloneSetup(profesorController).build();

        // Realizar una solicitud HTTP GET para buscar un profesor y esperar un estado de éxito (2xx)
        mockMvc.perform(MockMvcRequestBuilders.get("/profesor/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }

    // Prueba del método modificarProfesor
    @Test
    public void modificarProfesorTest() throws Exception {
        // Configurar el comportamiento del servicio al llamar a modificarProfesor
        Mockito.when(profesorService.modificarProfesor(Mockito.anyLong(), Mockito.any(ProfesorDto.class)))
                .thenReturn(new Profesor());

        // Configurar el controlador para la prueba
        this.mockMvc = MockMvcBuilders.standaloneSetup(profesorController).build();

        // Realizar una solicitud HTTP PUT para modificar un profesor por su ID y esperar un estado de éxito (2xx)
        mockMvc.perform(MockMvcRequestBuilders.put("/profesor/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nombre\":\"Juan\",\"apellido\":\"Perez\",\"dni\":123456}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }

    // Prueba del método borrarProfesor
    @Test
    public void borrarProfesorTest() throws Exception {
        // Configurar el comportamiento del servicio al llamar a borrarProfesor
        Mockito.when(profesorService.borrarProfesor(Mockito.anyLong())).thenReturn("Profesor eliminado exitosamente");

        // Configurar el controlador para la prueba
        this.mockMvc = MockMvcBuilders.standaloneSetup(profesorController).build();

        // Realizar una solicitud HTTP DELETE para borrar un profesor por su ID y esperar un estado de éxito (2xx)
        mockMvc.perform(MockMvcRequestBuilders.delete("/profesor/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }

    // Prueba del método obtenerMateriasDictadasProfesor
    @Test
    public void obtenerMateriasDictadasProfesorTest() throws Exception {
        // Configurar el comportamiento del servicio al llamar a obtenerMateriasDictadasProfesor
        Mockito.when(profesorService.obtenerMateriasDictadasProfesor(Mockito.anyLong())).thenReturn(Collections.emptyList());

        // Configurar el controlador para la prueba
        this.mockMvc = MockMvcBuilders.standaloneSetup(profesorController).build();

        // Realizar una solicitud HTTP GET para obtener las materias dictadas por un profesor y esperar un estado de éxito (2xx)
        mockMvc.perform(MockMvcRequestBuilders.get("/profesor/1/materias")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }
}
