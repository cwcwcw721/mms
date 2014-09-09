package org.yuxing.javaweb.jersey.jaxb;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.yuxing.javaweb.jersey.jaxb.model.ToDo;

@Path("/todo")
public class TodoResource {
	// This method is called if XMLis request
	  @GET
	  @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	  public ToDo getXML() {
	    ToDo todo = new ToDo();
	    todo.setSummary("This is my first todo");
	    todo.setDescription("This is my first todo");
	    return todo;
	  }
	  
	  // This can be used to test the integration with the browser
	  @GET
	  @Produces({ MediaType.TEXT_XML })
	  public ToDo getHTML() {
		ToDo todo = new ToDo();
	    todo.setSummary("This is my first todo");
	    todo.setDescription("This is my first todo");
	    return todo;
	  }
}
