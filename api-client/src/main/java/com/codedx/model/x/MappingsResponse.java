/**************************************************************************
 * Copyright (c) 2014 Applied Visions, Inc. All Rights Reserved.
 * Author: Applied Visions, Inc. - Chris Ellsworth
 * Project: Code Dx
 * SubSystem: com.secdec.codedx.model.x
 * FileName: MappingsResponse.java
 *************************************************************************/
package com.codedx.model.x;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Chris Ellsworth
 */
public class MappingsResponse implements Serializable {
	private static final long serialVersionUID = -296701588323249465L;

	private final Map<String, Object> any = new HashMap<String, Object>();

	@JsonAnyGetter
	public Map<String, Object> any() {
		return this.any;
	}

	@JsonAnySetter
	public void set(String name, Object value) {
		this.any.put(name, value);
	}

	@Override
	public String toString() {
		return "MappingsResponse{" +
				"any=" + any +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof MappingsResponse)) return false;

		MappingsResponse that = (MappingsResponse) o;

		return any.equals(that.any);
	}

	@Override
	public int hashCode() {
		return any.hashCode();
	}
}
