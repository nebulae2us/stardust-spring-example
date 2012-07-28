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

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.nebulae2us.electron.util.Immutables;
import org.nebulae2us.stardust.DaoManager;
import org.nebulae2us.stardust.Filter;
import org.nebulae2us.stardust.dao.JdbcExecutor;
import org.nebulae2us.stardust.example.model.Comment;
import org.nebulae2us.stardust.example.model.Person;
import org.nebulae2us.stardust.example.model.Tag;
import org.nebulae2us.stardust.example.service.CommentService;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Trung Phan
 *
 */
public class CommentServiceImpl implements CommentService {
	
	private DaoManager daoManager;
	
	private JdbcExecutor jdbcExecutor;

	/*
	 * (non-Javadoc)
	 * @see org.nebulae2us.stardust.example.service.CommentService#postComment(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Transactional
	public void postComment(String firstName, String lastName, String text, String tags) {
		
		Person person = retrieveOrCreatePerson(firstName, lastName);
		
		Comment comment = new Comment(text, person, new Date());
		daoManager.save(comment);
		
		List<String> tagList = Immutables.$(tags.split(",")).trimElement().removeEmpty();
		addTagsToDatabase(tagList);
		
		if (tagList.size() > 0) {
			List<Long> tagIds = jdbcExecutor.queryForListOfLong("select tag_id from tag where name in (:tagList)", Collections.singletonMap("tagList", tagList));
			for (Long tagId : tagIds) {
				jdbcExecutor.update("insert into comment_tag (comment_id, tag_id) values (?, ?)", Arrays.asList(comment.getCommentId(), tagId));
			}
		}
		
	}
	
	/* (non-Javadoc)
	 * @see org.nebulae2us.stardust.example.service.CommentService#updateComment(java.lang.Long, java.lang.String, java.lang.String)
	 */
	@Transactional
	public void updateComment(Long commentId, String text, String tags) {

		Comment comment = daoManager.get(Comment.class, commentId);
		comment.setText(text);
		comment.setUpdatedDate(new Date());
		daoManager.update(comment);

		
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
	
	private void addTagsToDatabase(List<String> tagList) {
		if (tagList.size() > 0) {
			List<String> existTags = jdbcExecutor.queryForListOfString("select name from tag where name in (:tagList)", Collections.singletonMap("tagList", tagList));
			List<String> newTags = Immutables.$(tagList).minus(existTags);
			for (String _tag : newTags) {
				Tag tag = new Tag(_tag);
				daoManager.save(tag);
			}
		}
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

	public final void setJdbcExecutor(JdbcExecutor jdbcExecutor) {
		this.jdbcExecutor = jdbcExecutor;
	}


}
