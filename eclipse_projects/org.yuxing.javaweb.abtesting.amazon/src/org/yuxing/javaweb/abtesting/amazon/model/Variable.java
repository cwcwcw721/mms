package org.yuxing.javaweb.abtesting.amazon.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Variable<T>{

	public enum VariableType {
		BOOLEAN,
		DOUBLE,
		FLOAT,
		INTEGER,
		LONG,
		SHORT,
		STRING
	}
	
	public VariableType Type;
	public T Value;
}
