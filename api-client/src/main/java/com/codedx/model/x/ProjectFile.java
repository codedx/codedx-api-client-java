/**************************************************************************
 * Copyright (c) 2014 Applied Visions, Inc. All Rights Reserved.
 * Author: Applied Visions, Inc. - Chris Ellsworth
 * Project: Code Dx
 * SubSystem: com.secdec.codedx.model.x
 * FileName: Mapping.java
 *************************************************************************/
package com.codedx.model.x;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * @author Chris Ellsworth
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjectFile implements Serializable {
	private static final long serialVersionUID = -6451029368303845996L;
	private Long id;
	private String path;
	private String pathType;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPath() {
		return this.path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getPathType() {
		return pathType;
	}

	public void setPathType(String pathType) {
		this.pathType = pathType;
	}

	@Override
	public String toString() {
		return "ProjectFile [id=" + this.id + ", pathType=" + this.pathType + ", path=" + this.path + "]";
	}

	public String asFullPathCriteria() {
		return "!{" + getPathType() + "}" + getPath();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof ProjectFile)) return false;

		ProjectFile that = (ProjectFile) o;

		if (!getId().equals(that.getId())) return false;
		if (!getPath().equals(that.getPath())) return false;
		return getPathType().equals(that.getPathType());
	}

	@Override
	public int hashCode() {
		int result = getId().hashCode();
		result = 31 * result + getPath().hashCode();
		result = 31 * result + getPathType().hashCode();
		return result;
	}
}
