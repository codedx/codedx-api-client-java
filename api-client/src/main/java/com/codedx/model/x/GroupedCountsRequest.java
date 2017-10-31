/**************************************************************************
 * Copyright (c) 2014 Applied Visions, Inc. All Rights Reserved.
 * Author: Applied Visions, Inc. - Chris Ellsworth
 * Project: Code Dx
 * SubSystem: com.secdec.codedx.model.x
 * FileName: GroupedCountsRequest.java
 *************************************************************************/
package com.codedx.model.x;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * @author Chris Ellsworth
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GroupedCountsRequest implements Serializable {
	public static final String COUNT_BY_SEVERITY = "severity";
	private static final long serialVersionUID = -5073458398915464523L;
	private Filter filter;
	private String countBy;

	public Filter getFilter() {
		return this.filter;
	}

	public void setFilter(Filter filter) {
		this.filter = filter;
	}

	public String getCountBy() {
		return this.countBy;
	}

	public void setCountBy(String countBy) {
		this.countBy = countBy;
	}

	@Override
	public String toString() {
		return "GroupedCountsRequest{" +
				"filter=" + filter +
				", countBy='" + countBy + '\'' +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof GroupedCountsRequest)) return false;

		GroupedCountsRequest that = (GroupedCountsRequest) o;

		if (getFilter() != null ? !getFilter().equals(that.getFilter()) : that.getFilter() != null) return false;
		return getCountBy() != null ? getCountBy().equals(that.getCountBy()) : that.getCountBy() == null;
	}

	@Override
	public int hashCode() {
		int result = getFilter() != null ? getFilter().hashCode() : 0;
		result = 31 * result + (getCountBy() != null ? getCountBy().hashCode() : 0);
		return result;
	}
}
