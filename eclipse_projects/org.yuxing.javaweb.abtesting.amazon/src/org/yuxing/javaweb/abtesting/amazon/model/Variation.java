package org.yuxing.javaweb.abtesting.amazon.model;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;

@XmlRootElement
public class Variation {
	private String _name;
	private String _projectName;
	private ArrayList _varaibles;

	public Variation(){
		this._name = "";
		this._projectName = "";
		this._varaibles = new ArrayList();
	}
	public String getName() {
		return this._name;
	}

	public String getProjectName() {
		return this._projectName;
	}

	public void setName(String name) {
		this._name = name;
	}
	
	public void setProjectName(String name) {
		this._projectName = name;
	}
	
	public Object getVariableByIndex(int index){
		if(index >= 0 && index < this._varaibles.size())
			return this._varaibles.get(index);
		else
			return null;
	}
	
	public Boolean addVariable(Object obj) {
		return this._varaibles.add(obj);
	}
}