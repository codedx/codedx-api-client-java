/**************************************************************************
 * Copyright (c) 2014 Applied Visions, Inc. All Rights Reserved.
 * Author: Applied Visions, Inc. - Chris Ellsworth
 * Project: Code Dx
 * SubSystem: com.secdec.codedx.model.x
 * FileName: Query.java
 *************************************************************************/
package com.codedx.model.x;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import java.io.Serializable;

/**
 * @author Chris Ellsworth
 */
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Query implements Serializable {
	private static final long serialVersionUID = -3528796829379755773L;
	private Filter filter;
	private Sort sort;
	private Pagination pagination;

	public Filter getFilter() {
		return this.filter;
	}

	public void setFilter(Filter filter) {
		this.filter = filter;
	}

	public Sort getSort() {
		return this.sort;
	}

	public void setSort(Sort sort) {
		this.sort = sort;
	}

	public Pagination getPagination() {
		return this.pagination;
	}

	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}

	@Override
	public String toString() {
		return "Query [filter=" + this.filter + ", sort=" + this.sort + ", pagination=" + this.pagination + "]";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Query)) return false;

		Query query = (Query) o;

		if (!getFilter().equals(query.getFilter())) return false;
		if (!getSort().equals(query.getSort())) return false;
		return getPagination().equals(query.getPagination());
	}

	@Override
	public int hashCode() {
		int result = getFilter().hashCode();
		result = 31 * result + getSort().hashCode();
		result = 31 * result + getPagination().hashCode();
		return result;
	}
}
