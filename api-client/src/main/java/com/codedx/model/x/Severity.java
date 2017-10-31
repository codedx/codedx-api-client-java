package com.codedx.model.x;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Severity implements Serializable {

	private static final long serialVersionUID = -6345094683975871784L;

	private final String name;
	private final int key;

	public int getKey() {
		return key;
	}

	public String getName() {
		return name;
	}

	@JsonCreator
	public Severity(@JsonProperty("name") String name, @JsonProperty("key") int key){
		this.name = name;
		this.key = key;
	}

	@Override
	public String toString() {
		return "Severity [key=" + key + ", name=" + name + "]";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Severity)) return false;

		Severity severity = (Severity) o;

		if (getKey() != severity.getKey()) return false;
		return getName().equals(severity.getName());
	}

	@Override
	public int hashCode() {
		int result = getName().hashCode();
		result = 31 * result + getKey();
		return result;
	}
}
