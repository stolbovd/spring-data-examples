package ru.inkontext.rest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * Created by stolbovd on 08.09.16.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class DecoratedClassTest {

	MockMvc mockMvc;

	@Autowired
	private WebApplicationContext context;

	@Before
	public void setUp() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
	}

	@Test
	public void findPerson() throws Exception {
			mockMvc.perform(get("/rest/persons/1"))
					.andExpect(status().isOk())
					.andExpect(jsonPath("id").value(1))
					.andExpect(jsonPath("decoratedClass").doesNotExist());
	}

	@Test
	public void findPersonProjected() throws Exception {
		mockMvc.perform(get("/rest/persons/1/projected"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("id").value(1))
				.andExpect(jsonPath("decoratedClass").doesNotExist());
	}

	@Test
	public void findPersonProjectedClass() throws Exception {
		mockMvc.perform(get("/rest/persons/1/projectedClass"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("id").value(1))
				.andExpect(jsonPath("decoratedClass").doesNotExist());
	}

	@Test
	public void findPersonAdressCity() throws Exception {
		mockMvc.perform(get("/rest/persons/1/adressCity"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("id").value(1))
				.andExpect(jsonPath("decoratedClass").doesNotExist());
	}

	@Test
	public void findPersonByAPI() throws Exception {
		mockMvc.perform(get("/rest/api/persons/1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("id").doesNotExist())
				.andExpect(jsonPath("_links").exists());
	}

	@Test
	public void findPersonByAPIWithProjection() throws Exception {
		mockMvc.perform(get("/rest/api/persons/1?projection=adressCity"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("id").value(1))
				.andExpect(jsonPath("_links").exists());
	}

	@Test
	public void findByAdress_Id() throws Exception {
		mockMvc.perform(get("/rest/api/persons/search/findByAdressId?adress=2&projection=adressCity"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("_embedded.persons").exists())
				.andExpect(jsonPath("_embedded.persons[0].id").value(2))
				.andExpect(jsonPath("_embedded.persons[0].adress.id").value(2));
	}
}