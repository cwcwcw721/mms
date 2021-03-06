package org.yuxing.javaweb.jersey.todo.dao;

import java.util.HashMap;
import java.util.Map;
import org.yuxing.javaweb.jersey.todo.model.ToDo;

public enum TodoDao {
	instance;

	private Map<String, ToDo> contentProvider = new HashMap<String, ToDo>();

	private TodoDao() {

		ToDo todo = new ToDo("1", "Learn REST");
		todo.setDescription("Read http://www.vogella.com/tutorials/REST/article.html");
		contentProvider.put("1", todo);
		todo = new ToDo("2", "Do something");
		todo.setDescription("Read complete http://www.vogella.com");
		contentProvider.put("2", todo);

	}

	public Map<String, ToDo> getModel() {
		return contentProvider;
	}
}
