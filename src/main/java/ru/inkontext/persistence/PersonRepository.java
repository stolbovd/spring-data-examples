/*
 * Copyright 2012-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ru.inkontext.persistence;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.inkontext.domain.Person;
import ru.inkontext.domain.projections.PersonCityProjection;

import java.util.List;

public interface PersonRepository extends CrudRepository<Person, Long> {

	@Query("select p from Person p left join p.adress a order by a.city, a.street")
	List<Person> findAllOrderByAdress();

	@Query("select p, a from Person p left join p.adress a where p.id = ?1")
	Person findById(Long id);

	PersonCityProjection findProjectedById(Long id);

	<T> T findProjectedClassById(Long id, Class<T> projection);

	List<Person> findByAdressId(@Param("adress") Long adressId);

	List<PersonCityProjection> findProjectedByAdressId(@Param("adress") Long adressId); //ToDo do not work
}
