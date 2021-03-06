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

package ru.inkontext.domain.projections;

import org.springframework.data.rest.core.config.Projection;
import ru.inkontext.domain.Person;

import java.util.Date;

@Projection(name = "full", types = Person.class)
public interface PersonFullProjection {

	Long getId();

	String getName();

	Date getBirthday();

	AdressCityProjection getAdress();
}
