/**************************************************************************
 * Copyright (c) 2014 Applied Visions, Inc. All Rights Reserved.
 * Author: Applied Visions, Inc. - Chris Ellsworth
 * Project: Code Dx
 * SubSystem: com.secdec.codedx.model.x
 * FileName: Cwe.java
 *************************************************************************/
package com.codedx.model.x;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * @author Chris Ellsworth
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Cwe implements Serializable {
	public static final String NO_CWE_ID = "none";

	private static final long serialVersionUID = 1690302230359123836L;
	private final int id;
	private final String name;

	public int getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	@JsonCreator
	public Cwe(@JsonProperty("id")int id, @JsonProperty("name")String name){
		this.id = id;
		this.name = name;
	}

	@Override
	public String toString() {
		return "Cwe{" +
				"id=" + id +
				", name='" + name + '\'' +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Cwe)) return false;

		Cwe cwe = (Cwe) o;

		if (getId() != cwe.getId()) return false;
		return getName() != null ? getName().equals(cwe.getName()) : cwe.getName() == null;
	}

	@Override
	public int hashCode() {
		int result = getId();
		result = 31 * result + (getName() != null ? getName().hashCode() : 0);
		return result;
	}
}
