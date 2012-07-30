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
package org.nebulae2us.stardust.example.service.impl;

import java.util.List;

import org.nebulae2us.stardust.DaoManager;
import org.nebulae2us.stardust.Filter;
import org.nebulae2us.stardust.Query;
import org.nebulae2us.stardust.example.domain.Comment;
import org.nebulae2us.stardust.example.domain.DomainContext;
import org.nebulae2us.stardust.example.domain.Person;
import org.nebulae2us.stardust.example.domain.Tag;
import org.nebulae2us.stardust.example.service.CommentService;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Trung Phan
 *
 */
public class CommentServiceImpl implements CommentService {
	
	private DaoManager daoManager;
	
	private DomainContext domainContext;

	/*
	 * (non-Javadoc)
	 * @see org.nebulae2us.stardust.example.service.CommentService#postComment(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Transactional
	public void postComment(String firstName, String lastName, String text, String tags) {
		Person person = retrieveOrCreatePerson(firstName, lastName);
		person.postComment(domainContext, text, tags);
	}
	
	/* (non-Javadoc)
	 * @see org.nebulae2us.stardust.example.service.CommentService#updateComment(java.lang.Long, java.lang.String, java.lang.String)
	 */
	@Transactional
	public void updateComment(Long personId, Long commentId, String text, String tags) {
		Person person = daoManager.get(Person.class, personId);
		person.updateComment(domainContext, commentId, text, tags);
	}
	
	/* (non-Javadoc)
	 * @see org.nebulae2us.stardust.example.service.CommentService#deleteComment(java.lang.Long)
	 */
	public void deleteComment(Long personId, Long commentId) {
		Person person = daoManager.get(Person.class, personId);
		person.deleteComment(domainContext, commentId);
	}

	private Person retrieveOrCreatePerson(String firstName, String lastName) {
		Person person = daoManager.newQuery(Person.class)
				.filterBy("personName.firstName = ?", firstName)
				.filterBy("personName.lastName = ?", lastName)
				.uniqueValue();
			
		if (person == null) {
			person = new Person(firstName, lastName);
			daoManager.save(person);
		}

		return person;
	}
	

	/*
	 * (non-Javadoc)
	 * @see org.nebulae2us.stardust.example.service.CommentService#getComments(java.util.List)
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Comment> getComments(List<Filter> filters) {

		List<Comment> comments = daoManager.newQuery(Comment.class)
				.innerJoin("commenter", "p")
				.outerJoin("tags", "t")
				.filterBy(filters)
				.orderBy("createdDate")
				.orderBy("t.name")
				.list();
		
		return comments;
	}

	/* (non-Javadoc)
	 * @see org.nebulae2us.stardust.example.service.CommentService#getCommentsByTag(java.lang.Long)
	 */
	public List<Comment> getCommentsByTag(Long tagId) {
		Query<?> subQuery = daoManager.newQuery(Comment.class)
				.innerJoin("tags", "t")
				.select("commentId")
				.filterBy("t.tagId = ?", tagId)
				.toQuery();
		
		List<Comment> comments = daoManager.newQuery(Comment.class)
				.innerJoin("commenter", "p")
				.outerJoin("tags", "t")
				.filterBy("commentId in (?)", subQuery)
				.orderBy("createdDate")
				.orderBy("t.name")
				.list();
		
		return comments;
	}

	/* (non-Javadoc)
	 * @see org.nebulae2us.stardust.example.service.CommentService#getPerson(java.lang.Long)
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public Person getPerson(Long personId) {
		return daoManager.get(Person.class, personId);
	}

	/* (non-Javadoc)
	 * @see org.nebulae2us.stardust.example.service.CommentService#getTag(java.lang.Long)
	 */
	public Tag getTag(Long tagId) {
		return daoManager.get(Tag.class, tagId);
	}

	public final void setDaoManager(DaoManager daoManager) {
		this.daoManager = daoManager;
	}

	public final void setDomainContext(DomainContext domainContext) {
		this.domainContext = domainContext;
	}


}
