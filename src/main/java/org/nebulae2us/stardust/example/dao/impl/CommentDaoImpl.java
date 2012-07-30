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
package org.nebulae2us.stardust.example.dao.impl;

import java.util.List;

import org.nebulae2us.electron.util.Immutables;
import org.nebulae2us.electron.util.MapBuilder;
import org.nebulae2us.stardust.DaoManager;
import org.nebulae2us.stardust.dao.JdbcExecutor;
import org.nebulae2us.stardust.example.dao.CommentDao;
import org.nebulae2us.stardust.example.domain.Tag;

/**
 * @author Trung Phan
 *
 */
public class CommentDaoImpl implements CommentDao {

	private DaoManager daoManager;
	
	private JdbcExecutor jdbcExecutor;

	public final void setDaoManager(DaoManager daoManager) {
		this.daoManager = daoManager;
	}

	public final void setJdbcExecutor(JdbcExecutor jdbcExecutor) {
		this.jdbcExecutor = jdbcExecutor;
	}

	/* (non-Javadoc)
	 * @see org.nebulae2us.stardust.example.dao.CommentDao#assignTagsToComment(java.util.List)
	 */
	public void assignTagsToComment(Long commentId, List<String> newTags) {
		
		List<String> existTags = daoManager.newQuery(Tag.class)
				.innerJoin("comments", "c")
				.select("name")
				.filterBy("c.commentId = ?", commentId)
				.listOfString();
		
		List<String> tagsToRemove = Immutables.$(existTags).minus(newTags);
		deassociateTagsFromComment(commentId, tagsToRemove);
		
		List<String> tagsToAdd = Immutables.$(newTags).minus(existTags);
		addTagsToDatabase(tagsToAdd);
		associateTagsToComment(commentId, tagsToAdd);
	}
	
	private void deassociateTagsFromComment(Long commentId, List<String> tags) {
		
		if (!tags.isEmpty()) {
			jdbcExecutor.update("delete from comment_tag where comment_id = :commentId and tag_id in (select tag_id from tag where name in (:tags))",
					new MapBuilder<String, Object>().put("commentId", commentId).put("tags", tags).toMap()
					);
		}
		
	}

	private void associateTagsToComment(Long commentId, List<String> tags) {
		if (!tags.isEmpty()) {
			jdbcExecutor.update("insert into comment_tag (comment_id, tag_id) select :commentId comment_id, tag_id from tag t where t.name in (:tags)",
					new MapBuilder<String, Object>().put("commentId", commentId).put("tags", tags).toMap()
					);
		}
	}
	
	private void addTagsToDatabase(List<String> tagList) {
		
		if (tagList.size() > 0) {
			List<String> existTags = daoManager.newQuery(Tag.class)
					.select("name")
					.filterBy("name in (?)", tagList)
					.listOfString();
			
			List<String> newTags = Immutables.$(tagList).minus(existTags);
			for (String _tag : newTags) {
				Tag tag = new Tag(_tag);
				daoManager.save(tag);
			}
		}
	}

	
}
