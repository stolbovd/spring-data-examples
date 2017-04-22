package ru.inkontext.rest;

/**
 * Created by stolbovd on 06.08.14.
 */

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.inkontext.domain.Person;
import ru.inkontext.domain.projections.PersonCityProjection;
import ru.inkontext.persistence.PersonRepository;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Slf4j
@RestController
@RequestMapping(value = "/rest/persons")
public class PersonRestController {

	@Autowired
	ProjectionFactory projectionFactory;

	@Autowired
	PersonRepository personRepository;

	@RequestMapping
	public List<Person> findPersons() throws Exception {
		return personRepository.findAllOrderByAdress();
	}

	@RequestMapping(value = "search/adressCity", method = GET)
	public List<PersonCityProjection> findPersonsOrderByAdress() throws Exception {
		List<PersonCityProjection> projections = new ArrayList<>();
		personRepository.findAllOrderByAdress().forEach(entity ->
				projections.add(projectionFactory.createProjection(PersonCityProjection.class, entity)));
		return projections;
	}

	@RequestMapping(value = "{id}")
	public Person findPerson(@PathVariable("id") Long id) throws Exception {
		return personRepository.findById(id);
	}

	@RequestMapping(value = "{id}/projected")
	public PersonCityProjection findPersonProjected(@PathVariable("id") Long id) throws Exception {
		return personRepository.findProjectedById(id);
	}

	@RequestMapping(value = "{id}/projectedClass")
	public PersonCityProjection findPersonProjectedClass(@PathVariable("id") Long id) throws Exception {
		return personRepository.findProjectedClassById(id, PersonCityProjection.class);
	}

	@RequestMapping(value = "{id}/adressCity", method = GET)
	public PersonCityProjection findPersonAdressCity(@PathVariable("id") Long id) throws Exception {
		PersonCityProjection personCityProjection =
				projectionFactory.createProjection(PersonCityProjection.class,
				personRepository.findById(id));
		return personCityProjection;
	}
}
