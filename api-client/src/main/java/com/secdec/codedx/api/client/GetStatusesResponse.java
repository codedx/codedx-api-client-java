/**************************************************************************
 * Copyright (c) 2014 Applied Visions, Inc. All Rights Reserved.
 * Author: Applied Visions, Inc. - Chris Ellsworth
 * Project: Code Dx
 * SubSystem: com.secdec.codedx.model.x
 * FileName: Statuses.java
 *************************************************************************/
package com.secdec.codedx.api.client;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.secdec.codedx.api.client.data.Status;

/**
 * @author Chris Ellsworth
 *
 */
class GetStatusesResponse implements Serializable
{
	private static final long serialVersionUID = 1510720910303052710L;

	private final Map<String, Status> statuses = new HashMap<String, Status>();

	@JsonAnyGetter
	public Map<String, Status> getStatuses()
	{
		return this.statuses;
	}

	@JsonAnySetter
	public void setStatus(String id, Status status)
	{
		this.statuses.put(id, status);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return this.statuses.toString();
	}

}
