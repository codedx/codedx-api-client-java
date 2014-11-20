/**************************************************************************
* Copyright (c) 2014 Applied Visions, Inc. All Rights Reserved.
* Author: Applied Visions, Inc. - Chris Ellsworth
* Project: Code Dx
* SubSystem: com.secdec.codedx.model.x
* FileName: Pagination.java
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
public class Pagination implements Serializable
{
	
	private static final long serialVersionUID = -8342976478157796845L;
	
	private Long page;
	private Long perPage;

	public Long getPage()
	{
		return this.page;
	}

	public void setPage(Long page)
	{
		this.page = page;
	}

	public Long getPerPage()
	{
		return this.perPage;
	}

	public void setPerPage(Long perPage)
	{
		this.perPage = perPage;
	}

	@Override
	public String toString()
	{
		return "Pagination [page=" + this.page + ", perPage=" + this.perPage + "]";
	}

}
