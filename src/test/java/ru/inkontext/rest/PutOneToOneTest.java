package ru.inkontext.rest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by stolbovd on 22.04.17.
 * PUT-request with application/json media type payload can not update association @OneToOne by URI.
 *
 * This code not worked from Spring Boot 1.4.4.Release.
 * Code is working in Spring Boot 1.4.3.Release and do not working in 1.5.3.Release.
 *
 * POST-request with URI in payload is creating @OneToOne association in all version of Spring
 * Boot from 1.4.3 to 1.5.3.
 * Tests below are imaging matter of this issue.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class PutOneToOneTest {

	MockMvc mockMvc;

	@Autowired
	private WebApplicationContext context;

	@Before
	public void setUp() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
	}

	@Test
	public void putPersonWithNewAdress() throws Exception {

		MvcResult resultPost = mockMvc.perform(post("/rest/api/persons?projection=full")
				.contentType(APPLICATION_JSON_UTF8)
				.content("{\"name\": \"Vasiliy\", \"adress\": \"/rest/api/adresses/2\"}"))
				.andExpect(status().isCreated())
				.andReturn();
		String location = resultPost.getResponse().getHeader("Location");
		Long id = Long.parseLong(location.substring(location.lastIndexOf("/")+1));

		mockMvc.perform(get("/rest/api/persons/"+id+"?projection=full"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("id").value(id))
				.andExpect(jsonPath("name").value("Vasiliy"))
				.andExpect(jsonPath("adress.id").value(2))
				.andExpect(jsonPath("adress.city").value("Surgut"));

		mockMvc.perform(put("/rest/api/persons/"+id+"?projection=full")
				.contentType(APPLICATION_JSON_UTF8)
				.content("{\"name\": \"Maria\", \"adress\": \"/rest/api/adresses/4\"}"))
				.andExpect(status().isNoContent());

		mockMvc.perform(get("/rest/api/persons/"+id+"?projection=full"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("id").value(id))
				.andExpect(jsonPath("name").value("Maria"))
				.andExpect(jsonPath("adress.id").value(4))
				.andExpect(jsonPath("adress.city").value("Nefteugansk"));
	}
}