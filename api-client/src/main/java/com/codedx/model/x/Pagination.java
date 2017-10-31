/**************************************************************************
 * Copyright (c) 2014 Applied Visions, Inc. All Rights Reserved.
 * Author: Applied Visions, Inc. - Chris Ellsworth
 * Project: Code Dx
 * SubSystem: com.secdec.codedx.model.x
 * FileName: Pagination.java
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
public class Pagination implements Serializable {
	private static final long serialVersionUID = 8726309792660605211L;
	private Long page;
	private Long perPage;

	public Long getPage() {
		return this.page;
	}

	public void setPage(Long page) {
		this.page = page;
	}

	public Long getPerPage() {
		return this.perPage;
	}

	public void setPerPage(Long perPage) {
		this.perPage = perPage;
	}

	@Override
	public String toString() {
		return "Pagination{" +
				"page=" + page +
				", perPage=" + perPage +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Pagination)) return false;

		Pagination that = (Pagination) o;

		if (getPage() != null ? !getPage().equals(that.getPage()) : that.getPage() != null) return false;
		return getPerPage() != null ? getPerPage().equals(that.getPerPage()) : that.getPerPage() == null;
	}

	@Override
	public int hashCode() {
		int result = getPage() != null ? getPage().hashCode() : 0;
		result = 31 * result + (getPerPage() != null ? getPerPage().hashCode() : 0);
		return result;
	}
}
