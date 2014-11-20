/**************************************************************************
 * Copyright (c) 2014 Applied Visions, Inc. All Rights Reserved.
 * Author: Applied Visions, Inc. - Chris Ellsworth
 * Project: Code Dx
 * SubSystem: com.secdec.codedx.model.x
 * FileName: Query.java
 *************************************************************************/
package com.secdec.codedx.api.client.data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author Chris Ellsworth
 *
 */
@JsonInclude(Include.NON_NULL)
public class TableQuery implements Serializable
{
	private static final long serialVersionUID = -4814589128172536839L;
	
	private Filter filter;
	private Sort sort;
	private Pagination pagination;

	public Filter getFilter()
	{
		return this.filter;
	}

	public void setFilter(Filter filter)
	{
		this.filter = filter;
	}

	public Sort getSort()
	{
		return this.sort;
	}

	public void setSort(Sort sort)
	{
		this.sort = sort;
	}

	public Pagination getPagination()
	{
		return this.pagination;
	}

	public void setPagination(Pagination pagination)
	{
		this.pagination = pagination;
	}

	@Override
	public String toString()
	{
		return "Query [filter=" + this.filter + ", sort=" + this.sort + ", pagination=" + this.pagination + "]";
	}
}
