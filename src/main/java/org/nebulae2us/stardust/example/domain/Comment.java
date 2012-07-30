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

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.nebulae2us.electron.util.Immutables;
import org.nebulae2us.stardust.DaoManager;
import org.nebulae2us.stardust.example.dao.CommentDao;

/**
 * @author Trung Phan
 *
 */
@Entity
public class Comment {

	public Comment() {}
	
	public Comment(String text, Person commenter, Date createdDate) {
		this.text = text;
		this.commenter = commenter;
		this.createdDate = createdDate;
	}
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comment_seq")
	@SequenceGenerator(name = "comment_seq", sequenceName = "comment_seq")
	private Long commentId;
	
	private String text;

	@ManyToOne
	private Person commenter;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;
	
	private Date updatedDate;

	@ManyToMany
	@JoinTable(name = "COMMENT_TAG", 
		joinColumns=@JoinColumn(name = "COMMENT_ID", referencedColumnName = "COMMENT_ID"),
		inverseJoinColumns=@JoinColumn(name = "TAG_ID", referencedColumnName = "TAG_ID"))
	private List<Tag> tags;

	public final Long getCommentId() {
		return commentId;
	}

	public final void setCommentId(Long commentId) {
		this.commentId = commentId;
	}

	public final String getText() {
		return text;
	}

	public final void setText(String text) {
		this.text = text;
	}

	public final Person getCommenter() {
		return commenter;
	}

	public final void setCommenter(Person commenter) {
		this.commenter = commenter;
	}

	public final Date getCreatedDate() {
		return createdDate;
	}

	public final void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public final Date getUpdatedDate() {
		return updatedDate;
	}

	public final void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public final List<Tag> getTags() {
		return tags;
	}

	public final String getTagsAsCSV() {
		StringBuilder result = new StringBuilder();
		if (tags != null) {
			for (Tag tag : tags) {
				if (result.length() > 0) {
					result.append(", ");
				}
				result.append(tag.getName());
			}
		}
		return result.toString();
	}
	
	public void updateComment(DomainContext domainContext, String newText, String newTagsAsCSV) {
		DaoManager daoManager = domainContext.getDaoManager();
		
		this.text = newText;
		this.updatedDate = new Date();
		daoManager.update(this);
		
		this.assignTags(domainContext, newTagsAsCSV);
	}	
	
	public void assignTags(DomainContext context, String tagsAsCSV) {
		CommentDao commentDao = context.getCommentDao();
		
		List<String> newTags = Immutables.$(tagsAsCSV.split(",")).trimElement().removeEmpty();
		commentDao.assignTagsToComment(commentId, newTags);
		
	}
	
	public void removeAllTags(DomainContext context) {
		CommentDao commentDao = context.getCommentDao();
		commentDao.assignTagsToComment(commentId, Immutables.emptyStringList());
	}
	
}
