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

import org.nebulae2us.stardust.DaoManager;
import org.nebulae2us.stardust.example.dao.CommentDao;

/**
 * @author Trung Phan
 *
 */
public class DomainContext {

	private DaoManager daoManager;
	
	private CommentDao commentDao;
	
	public final DaoManager getDaoManager() {
		return daoManager;
	}

	public final void setDaoManager(DaoManager daoManager) {
		this.daoManager = daoManager;
	}

	public final CommentDao getCommentDao() {
		return commentDao;
	}

	public final void setCommentDao(CommentDao commentDao) {
		this.commentDao = commentDao;
	}
	
	
}
