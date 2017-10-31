/**************************************************************************
 * Copyright (c) 2014 Applied Visions, Inc. All Rights Reserved.
 * Author: Applied Visions, Inc. - Chris Ellsworth
 * Project: Code Dx
 * SubSystem: com.secdec.codedx.model.api
 * FileName: Projects.java
 *************************************************************************/
package com.codedx.model.api;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Projects implements Serializable {
	private static final long serialVersionUID = 5928043034977241254L;

	private final List<Project> projects;

	public List<Project> getProjects() {
		return projects;
	}

	@JsonCreator
	public Projects(@JsonProperty("projects")List<Project> projects){
		this.projects = projects;
	}

	@Override
	public String toString() {
		return "Projects{" +
				"projects=" + projects +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Projects)) return false;

		Projects projects1 = (Projects) o;

		return getProjects() != null ? getProjects().equals(projects1.getProjects()) : projects1.getProjects() == null;
	}

	@Override
	public int hashCode() {
		return getProjects() != null ? getProjects().hashCode() : 0;
	}
}
