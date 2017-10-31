package com.codedx.model.x;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Path implements Serializable {

	private static final long serialVersionUID = 3776053200884885571L;

	private final long id;
	private final String pathType;
	private final String path;
	private final String shortName;

	public long getId() {
		return id;
	}

	public String getPathType() {
		return this.pathType;
	}

	public String getPath() {
		return path;
	}

	public String getShortName() {
		return shortName;
	}

	@JsonCreator
	public Path(@JsonProperty("id") int id,
				@JsonProperty("pathType") String pathType,
				@JsonProperty("path") String path,
				@JsonProperty("shortName")String shortName){
		this.id = id;
		this.pathType = pathType;
		this.path = path;
		this.shortName = shortName;
	}

	@Override
	public String toString() {
		return "Path{" +
				"id=" + id +
				", pathType='" + pathType + '\'' +
				", path='" + path + '\'' +
				", shortName='" + shortName + '\'' +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Path)) return false;

		Path path1 = (Path) o;

		if (getId() != path1.getId()) return false;
		if (!getPathType().equals(path1.getPathType())) return false;
		if (!getPath().equals(path1.getPath())) return false;
		return getShortName().equals(path1.getShortName());
	}

	@Override
	public int hashCode() {
		int result = (int) (getId() ^ (getId() >>> 32));
		result = 31 * result + getPathType().hashCode();
		result = 31 * result + getPath().hashCode();
		result = 31 * result + getShortName().hashCode();
		return result;
	}
}
