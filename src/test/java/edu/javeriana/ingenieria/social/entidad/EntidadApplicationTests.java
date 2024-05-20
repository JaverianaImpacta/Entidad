package edu.javeriana.ingenieria.social.entidad;


import com.fasterxml.jackson.databind.ObjectMapper;
import edu.javeriana.ingenieria.social.entidad.dominio.Entidad;
import edu.javeriana.ingenieria.social.entidad.servicio.ServicioEntidad;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.sql.SQLException;
import java.util.List;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ControladorEntidadTest {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;


	@Autowired
	private JdbcTemplate jdbcTemplate;
	@BeforeEach
	public void limpiarYRecargarBaseDatos() throws SQLException {
		// Leer el script SQL desde el archivo
		ClassPathResource resource = new ClassPathResource("Script.sql");
		ScriptUtils.executeSqlScript(jdbcTemplate.getDataSource().getConnection(), resource);
		resource = new ClassPathResource("Inserts-Prueba.sql");
		ScriptUtils.executeSqlScript(jdbcTemplate.getDataSource().getConnection(), resource);
	}

	@Test
	void obtenerEntidades() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/entidades/listar"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(3));
	}

	@Test
	void obtenerEntidadesPorAprobacion() throws Exception {
		boolean aprobacion = true;

		mockMvc.perform(MockMvcRequestBuilders.get("/api/entidades/obtenerAprobacion?aprobacion=" + aprobacion))
				.andExpect(MockMvcResultMatchers.status().isNotFound());
		aprobacion = false;

		mockMvc.perform(MockMvcRequestBuilders.get("/api/entidades/obtenerAprobacion?aprobacion=" + aprobacion))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(3));
	}

	@Test
	void obtenerEntidadesPorActividadEconomica() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.get("/api/entidades/obtenerActividadEconomica")
						.param("actividadEconomica", "0510"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].actividadEconomica").value("0510"));
	}

	@Test
	void obtenerEntidad() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/entidades/obtener")
						.param("id", String.valueOf(1)))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
				.andExpect(MockMvcResultMatchers.jsonPath("$.nit").value(452454));;
	}
	@Test
	void obtenerEntidadPorConvenio() throws Exception {
		Integer convenio = 1001;

		mockMvc.perform(MockMvcRequestBuilders.get("/api/entidades/obtenerConvenio")
						.param("convenio", String.valueOf(convenio)))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
				.andExpect(MockMvcResultMatchers.jsonPath("$.convenio").value(convenio));
	}

	@Test
	void obtenerEntidadPorNit() throws Exception {
		Integer nit = 452454;

		mockMvc.perform(MockMvcRequestBuilders.get("/api/entidades/obtenerNit")
						.param("nit", String.valueOf(nit)))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
				.andExpect(MockMvcResultMatchers.jsonPath("$.nit").value(nit));
	}
	@Test
	void crearEntidad() throws Exception {
		Entidad nuevaEntidad = new Entidad();
		nuevaEntidad.setId(4);
		nuevaEntidad.setNit(7894561);
		nuevaEntidad.setConvenio(789);
		nuevaEntidad.setCorreo("nuevaentidad@example.com");
		nuevaEntidad.setActividadEconomica("0510");
		nuevaEntidad.setRazonSocial("Entidad Tecnológica");
		nuevaEntidad.setCedulaRepresentante(1234567);
		nuevaEntidad.setTelefono(12332143);
		nuevaEntidad.setAprobacion(true);
		nuevaEntidad.setDocumento("NI");

		mockMvc.perform(MockMvcRequestBuilders.post("/api/entidades/crear")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(nuevaEntidad)))
				.andExpect(MockMvcResultMatchers.status().isCreated())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(4));
	}

	@Test
	void actualizarEntidad() throws Exception {
		Integer idEntidad = 1;
		Entidad nuevaEntidad = new Entidad();
		nuevaEntidad.setId(idEntidad);
		nuevaEntidad.setNit(452454);
		nuevaEntidad.setConvenio(789);
		nuevaEntidad.setCorreo("nuevaentidad@example.com");
		nuevaEntidad.setActividadEconomica("0510");
		nuevaEntidad.setRazonSocial("Entidad Tecnológica");
		nuevaEntidad.setCedulaRepresentante(1234567);
		nuevaEntidad.setTelefono(12332143);
		nuevaEntidad.setAprobacion(true);
		nuevaEntidad.setDocumento("NI");

		mockMvc.perform(MockMvcRequestBuilders.put("/api/entidades/actualizar")
						.param("id", String.valueOf(idEntidad))
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(nuevaEntidad)))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(idEntidad));
	}
}
