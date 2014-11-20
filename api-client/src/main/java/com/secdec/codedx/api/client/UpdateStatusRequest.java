/**************************************************************************
* Copyright (c) 2014 Applied Visions, Inc. All Rights Reserved.
* Author: Applied Visions, Inc. - Chris Ellsworth
* Project: Code Dx
* SubSystem: com.secdec.codedx.model.x
* FileName: SetStatus.java
*************************************************************************/
package com.secdec.codedx.api.client;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.secdec.codedx.api.client.data.Filter;

/**
 * @author Chris Ellsworth
 *
 */
class UpdateStatusRequest implements Serializable
{
	private static final long serialVersionUID = -1445181097285354871L;
	
	@JsonInclude(Include.NON_NULL)
	private Filter filter;
	private String status;

	public Filter getFilter()
	{
		return this.filter;
	}

	public void setFilter(Filter filter)
	{
		this.filter = filter;
	}

	public String getStatus()
	{
		return this.status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}
}
