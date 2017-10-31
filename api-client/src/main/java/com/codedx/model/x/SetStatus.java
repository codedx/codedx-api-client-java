/**************************************************************************
 * Copyright (c) 2014 Applied Visions, Inc. All Rights Reserved.
 * Author: Applied Visions, Inc. - Chris Ellsworth
 * Project: Code Dx
 * SubSystem: com.secdec.codedx.model.x
 * FileName: SetStatus.java
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
public class SetStatus implements Serializable {
	private static final long serialVersionUID = -7845202036008705676L;
	@JsonInclude(Include.NON_NULL)
	private Filter filter;
	private String status;

	public Filter getFilter() {
		return this.filter;
	}

	public void setFilter(Filter filter) {
		this.filter = filter;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof SetStatus)) return false;

		SetStatus setStatus = (SetStatus) o;

		if (!getFilter().equals(setStatus.getFilter())) return false;
		return getStatus().equals(setStatus.getStatus());
	}

	@Override
	public int hashCode() {
		int result = getFilter().hashCode();
		result = 31 * result + getStatus().hashCode();
		return result;
	}
}
