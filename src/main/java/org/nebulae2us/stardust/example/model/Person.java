/*
 * Copyright 2012 the original author or authors.
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
package org.nebulae2us.stardust.example.model;

import java.util.List;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

/**
 * @author Trung Phan
 *
 */
@Entity
public class Person {
	
	public Person() {}
	
	public Person(String firstName, String lastName) {
		this.personName = new PersonName();
		this.personName.setFirstName(firstName);
		this.personName.setLastName(lastName);
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "person_seq")
	@SequenceGenerator(name = "person_seq", sequenceName = "person_seq")
	private Long personId;

	@Embedded
	private PersonName personName;
	
	@OneToMany(mappedBy = "commenter")
	private List<Comment> comments;

	public final Long getPersonId() {
		return personId;
	}

	public final void setPersonId(Long personId) {
		this.personId = personId;
	}

	public final PersonName getPersonName() {
		return personName;
	}

	public final void setPersonName(PersonName personName) {
		this.personName = personName;
	}

	public final List<Comment> getComments() {
		return comments;
	}

	public final void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	
	
}
