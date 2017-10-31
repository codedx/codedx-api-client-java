/**************************************************************************
 * Copyright (c) 2014 Applied Visions, Inc. All Rights Reserved.
 * Author: Applied Visions, Inc. - Chris Ellsworth
 * Project: Code Dx
 * SubSystem: com.secdec.codedx.model.x
 * FileName: Location.java
 *************************************************************************/
package com.codedx.model.x;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Location implements Serializable {
	private static final long serialVersionUID = 6835806279607433291L;

	private final Path path;
	private final LocationRange lines;
	private final LocationRange columns;
	private final Boolean hasSource;

	public Path getPath() {
		return this.path;
	}

	public LocationRange getLines() {
		return this.lines;
	}

	public LocationRange getColumns() {
		return this.columns;
	}

	public Boolean getHasSource() {
		return this.hasSource;
	}

	@JsonCreator
	public Location(@JsonProperty("path")Path path,
					@JsonProperty("lines")LocationRange lines,
					@JsonProperty("columns")LocationRange columns,
					@JsonProperty("hasSource")Boolean hasSource){
		this.path = path;
		this.lines = lines;
		this.columns = columns;
		this.hasSource = hasSource;
	}

	@Override
	public String toString() {
		return "Location{" +
				"path=" + path +
				", lines=" + lines +
				", columns=" + columns +
				", hasSource=" + hasSource +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Location)) return false;

		Location location = (Location) o;

		if (getPath() != null ? !getPath().equals(location.getPath()) : location.getPath() != null) return false;
		if (getLines() != null ? !getLines().equals(location.getLines()) : location.getLines() != null) return false;
		if (getColumns() != null ? !getColumns().equals(location.getColumns()) : location.getColumns() != null)
			return false;
		return getHasSource() != null ? getHasSource().equals(location.getHasSource()) : location.getHasSource() == null;
	}

	@Override
	public int hashCode() {
		int result = getPath() != null ? getPath().hashCode() : 0;
		result = 31 * result + (getLines() != null ? getLines().hashCode() : 0);
		result = 31 * result + (getColumns() != null ? getColumns().hashCode() : 0);
		result = 31 * result + (getHasSource() != null ? getHasSource().hashCode() : 0);
		return result;
	}
}
