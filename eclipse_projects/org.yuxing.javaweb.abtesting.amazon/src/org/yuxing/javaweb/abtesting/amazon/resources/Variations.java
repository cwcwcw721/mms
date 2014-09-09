package org.yuxing.javaweb.abtesting.amazon.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.yuxing.javaweb.abtesting.amazon.model.Variation;
import org.yuxing.javaweb.abtesting.amazon.model.Variable;

import static org.yuxing.javaweb.abtesting.amazon.CommonUtilities.*;
import android.test.mock.MockContext;

@Path("/variations")
public class Variations {
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Variation getXML() {

		Variation variation = new Variation();
		variation.setName("Name");
		variation.setProjectName("Project Name");
		Variable<String> v1 = new Variable<String>();
		v1.Type = Variable.VariableType.STRING;
		v1.Value = "variable";
		variation.addVariable(v1);
		return variation;
	}

	// This can be used to test the integration with the browser
	// @GET
	// @Produces({ MediaType.TEXT_XML })
	// public ToDo getHTML() {
	// ToDo todo = new ToDo();
	// todo.setSummary("This is my first todo");
	// todo.setDescription("This is my first todo");
	// return todo;
	// }

}
