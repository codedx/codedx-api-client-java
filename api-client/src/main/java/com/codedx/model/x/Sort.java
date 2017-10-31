/**************************************************************************
 * Copyright (c) 2014 Applied Visions, Inc. All Rights Reserved.
 * Author: Applied Visions, Inc. - Chris Ellsworth
 * Project: Code Dx
 * SubSystem: com.secdec.codedx.model.x
 * FileName: Sort.java
 *************************************************************************/
package com.codedx.model.x;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import java.io.Serializable;

/**
 * @author Chris Ellsworth
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class Sort implements Serializable {
	private static final long serialVersionUID = -7859187898018002237L;
	private String by;
	private String direction;

	public String getBy() {
		return this.by;
	}

	public void setBy(String by) {
		this.by = by;
	}

	public String getDirection() {
		return this.direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	@Override
	public String toString() {
		return "Sort [by=" + this.by + ", direction=" + this.direction + "]";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Sort)) return false;

		Sort sort = (Sort) o;

		if (!getBy().equals(sort.getBy())) return false;
		return getDirection().equals(sort.getDirection());
	}

	@Override
	public int hashCode() {
		int result = getBy().hashCode();
		result = 31 * result + getDirection().hashCode();
		return result;
	}
}
