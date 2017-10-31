/**************************************************************************
 * Copyright (c) 2014 Applied Visions, Inc. All Rights Reserved.
 * Author: Applied Visions, Inc. - Chris Ellsworth
 * Project: Code Dx
 * SubSystem: com.secdec.codedx.model.x
 * FileName: MappingsRequest.java
 *************************************************************************/
package com.codedx.model.x;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Chris Ellsworth
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MappingsRequest implements Serializable {
	private static final long serialVersionUID = 3631536726751669818L;

	private List<String> files;
	private final Map<String, Object> any = new HashMap<String, Object>();

	public List<String> getFiles() {
		return this.files;
	}

	public void setFiles(List<String> files) {
		this.files = files;
	}

	@JsonAnyGetter
	public Map<String, Object> any() {
		return this.any;
	}

	@JsonAnySetter
	public void set(String name, Object value){
		this.any.put(name, value);
	}

	@Override
	public String toString() {
		return "MappingsRequest{" +
				"files=" + files +
				", any=" + any +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof MappingsRequest)) return false;

		MappingsRequest that = (MappingsRequest) o;

		if (getFiles() != null ? !getFiles().equals(that.getFiles()) : that.getFiles() != null) return false;
		return any.equals(that.any);
	}

	@Override
	public int hashCode() {
		int result = getFiles() != null ? getFiles().hashCode() : 0;
		result = 31 * result + any.hashCode();
		return result;
	}
}
