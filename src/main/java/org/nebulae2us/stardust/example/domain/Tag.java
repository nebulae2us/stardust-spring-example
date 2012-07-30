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

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;

/**
 * @author Trung Phan
 *
 */
@Entity
public class Tag {

	public Tag() {}
	
	public Tag(String name) {
		this.name = name;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tag_seq")
	@SequenceGenerator(name = "tag_seq", sequenceName = "tag_seq")
	private Long tagId;
	
	private String name;
	
	@ManyToMany(mappedBy = "tags")
	private List<Comment> comments;

	public final Long getTagId() {
		return tagId;
	}

	public final void setTagId(Long tagId) {
		this.tagId = tagId;
	}

	public final String getName() {
		return name;
	}

	public final void setName(String name) {
		this.name = name;
	}

	public final List<Comment> getComments() {
		return comments;
	}

	public final void setComments(List<Comment> comments) {
		this.comments = comments;
	}
	
	
	
}
