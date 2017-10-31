/**************************************************************************
 * Copyright (c) 2014 Applied Visions, Inc. All Rights Reserved.
 * Author: Applied Visions, Inc. - Chris Ellsworth
 * Project: Code Dx
 * SubSystem: com.secdec.codedx.model.api
 * FileName: Project.java
 *************************************************************************/
package com.codedx.model.api;

import com.codedx.model.x.Statuses;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Project implements Serializable {
	private static final long serialVersionUID = -3380264248525154448L;
	private final int id;
	private final String name;
	@JsonIgnore
	private Statuses statuses;
	@JsonIgnore
	private boolean canChangeStatuses;

	public int getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public Statuses getStatuses() {
		return this.statuses;
	}

	public void setStatuse(Statuses statuses){
		this.statuses = statuses;
	}

	public boolean canChangeStatuses() {
		return canChangeStatuses;
	}

	public void setCanChangeStatuses(boolean canChangeStatuses){
		this.canChangeStatuses = canChangeStatuses;
	}

	@JsonCreator
	public Project(@JsonProperty("id") int id, @JsonProperty("name") String name){
		this.id = id;
		this.name = name;
	}

	@Override
	public String toString() {
		return "Project{" +
				"id=" + id +
				", name='" + name + '\'' +
				", statuses=" + statuses +
				", canChangeStatuses=" + canChangeStatuses +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Project)) return false;

		Project project = (Project) o;

		if (getId() != project.getId()) return false;
		if (canChangeStatuses != project.canChangeStatuses) return false;
		if (getName() != null ? !getName().equals(project.getName()) : project.getName() != null) return false;
		return getStatuses() != null ? getStatuses().equals(project.getStatuses()) : project.getStatuses() == null;
	}

	@Override
	public int hashCode() {
		int result = getId();
		result = 31 * result + (getName() != null ? getName().hashCode() : 0);
		result = 31 * result + (getStatuses() != null ? getStatuses().hashCode() : 0);
		result = 31 * result + (canChangeStatuses ? 1 : 0);
		return result;
	}
}
