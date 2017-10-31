/**************************************************************************
 * Copyright (c) 2014 Applied Visions, Inc. All Rights Reserved.
 * Author: Applied Visions, Inc. - Chris Ellsworth
 * Project: Code Dx
 * SubSystem: com.secdec.codedx.model.x
 * FileName: Filter.java
 *************************************************************************/
package com.codedx.model.x;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Chris Ellsworth
 */
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Filter implements Serializable {
	public static final String PATH = "path";
	public static final String FINDING = "finding";
	public static final String STATUS = "status";
	private static final long serialVersionUID = -6816433997693414141L;
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
		return "Filter{" +
				"any=" + any +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Filter)) return false;

		Filter filter = (Filter) o;

		return any.equals(filter.any);
	}

	@Override
	public int hashCode() {
		return any.hashCode();
	}
}
