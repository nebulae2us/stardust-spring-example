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
package org.nebulae2us.stardust.example.service;

import java.util.List;

import org.nebulae2us.stardust.Filter;
import org.nebulae2us.stardust.example.domain.Comment;
import org.nebulae2us.stardust.example.domain.Person;
import org.nebulae2us.stardust.example.domain.Tag;

/**
 * @author Trung Phan
 *
 */
public interface CommentService {
	
	/**
	 * Create new {@link Comment}
	 * 
	 * @param firstName
	 * @param lastName
	 * @param text
	 * @param tags
	 */
	public void postComment(String firstName, String lastName, String text, String tags);
	
	/**
	 * Update existing {@link Comment}
	 * @param commentId
	 * @param text
	 * @param tags
	 */
	public void updateComment(Long personId, Long commentId, String text, String tags);
	
	/**
	 * Delete the {@link Comment}
	 * @param commentId
	 */
	public void deleteComment(Long personId, Long commentId);
	
	/**
	 * Retrieve list of {@link Comment}s. Filter the list of filters are provided.
	 * @param filters
	 * @return
	 */
	public List<Comment> getComments(List<Filter> filters);

	/**
	 * Retrieve list of {@link Comment}s for a particular tagId.
	 * 
	 * @param tagId
	 * @return
	 */
	public List<Comment> getCommentsByTag(Long tagId);

	/**
	 * Retrieve {@link Person}
	 * @param personId
	 * @return
	 */
	public Person getPerson(Long personId);
	
	/**
	 * Retrieve {@link Tag}
	 * @param tagId
	 * @return
	 */
	public Tag getTag(Long tagId);

}
