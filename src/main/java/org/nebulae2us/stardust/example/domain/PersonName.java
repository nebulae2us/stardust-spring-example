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
package org.nebulae2us.stardust.example.domain;

import org.nebulae2us.electron.Mirror;

/**
 * @author Trung Phan
 *
 */
public class PersonName {

	private final String firstName;
	
	private final String lastName;
	
	public PersonName(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	public PersonName(Mirror mirror) {
		mirror.bind(this);
		this.firstName = mirror.toString("firstName");
		this.lastName = mirror.toString("lastName");
	}

	public final String getFirstName() {
		return firstName;
	}

	public final String getLastName() {
		return lastName;
	}

	
}
