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
package org.nebulae2us.stardust.example.controller;

import java.util.Collections;
import java.util.List;

import org.nebulae2us.electron.util.Immutables;
import org.nebulae2us.electron.util.MapBuilder;
import org.nebulae2us.stardust.Filter;
import org.nebulae2us.stardust.example.model.Comment;
import org.nebulae2us.stardust.example.model.Person;
import org.nebulae2us.stardust.example.model.Tag;
import org.nebulae2us.stardust.example.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Trung Phan
 *
 */
@Controller
public class CommentController {

	@Autowired
	private CommentService commentService;
	
	@RequestMapping(value="/comment", method=RequestMethod.POST)
	public String addComment(@RequestParam(value="fn") String firstName, @RequestParam(value="sn") String lastName, @RequestParam(value="text") String text, @RequestParam(value="tags") String tags) {
		
		commentService.postComment(firstName, lastName, text, tags);
		
		return "redirect:/rest/comment";
	}
	
	@RequestMapping(value="/comment", method=RequestMethod.GET)
	public ModelAndView listComments() {
		
		List<Comment> comments = commentService.getComments(Immutables.emptyList(Filter.class));
		
		ModelAndView mav = new ModelAndView("listComments", Collections.singletonMap("comments", comments));

		return mav;
	}
	
	@RequestMapping(value="/comment/{id}", method=RequestMethod.GET)
	public ModelAndView editComment(@PathVariable("id") Long commentId) {
		
		List<Comment> comments = commentService.getComments(Immutables.emptyList(Filter.class));
		
		ModelAndView mav = new ModelAndView("editComment", new MapBuilder<String, Object>().put("comments", comments).put("commentIdToEdit", commentId).toMap());

		return mav;
	}
	
	@RequestMapping(value="/comment/{id}", method=RequestMethod.POST)
	public String saveComment(@PathVariable("id") Long commentId, @RequestParam(value="text") String text, @RequestParam(value="tags") String tags) {

		commentService.updateComment(commentId, text, tags);
		return "redirect:/rest/comment";
	}

	@RequestMapping(value="/user/{id}", method=RequestMethod.GET)
	public ModelAndView userDetail(@PathVariable("id") Long personId) {
		
		Person person = commentService.getPerson(personId);
		if (person == null) {
			throw new IllegalArgumentException("Cannot find person with id = " + personId);
		}
		
		Filter filter = new Filter("p.personId = ?", personId);
		List<Comment> comments = commentService.getComments(Immutables.$(filter));
		
		ModelAndView mav = new ModelAndView("user", new MapBuilder<String, Object>().put("comments", comments).put("commenter", person).toMap());

		return mav;
	}
	
	@RequestMapping(value="/tag/{id}", method=RequestMethod.GET)
	public ModelAndView tagDetail(@PathVariable("id") Long tagId) {
		
		Tag tag = commentService.getTag(tagId);
		if (tag == null) {
			throw new IllegalArgumentException("Cannot find tag with id = " + tagId);
		}
		
		Filter filter = new Filter("t.tagId = ?", tagId);
		List<Comment> comments = commentService.getComments(Immutables.$(filter));
		
		ModelAndView mav = new ModelAndView("tag", new MapBuilder<String, Object>().put("comments", comments).put("tag", tag).toMap());

		return mav;
	}
}
