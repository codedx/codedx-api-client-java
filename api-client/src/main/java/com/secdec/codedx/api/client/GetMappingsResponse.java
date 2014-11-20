/**************************************************************************
* Copyright (c) 2014 Applied Visions, Inc. All Rights Reserved.
* Author: Applied Visions, Inc. - Chris Ellsworth
* Project: Code Dx
* SubSystem: com.secdec.codedx.model.x
* FileName: MappingsResponse.java
*************************************************************************/
package com.secdec.codedx.api.client;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.secdec.codedx.api.client.data.ProjectFile;

/**
 * @author Chris Ellsworth
 *
 */
class GetMappingsResponse implements Serializable
{
	private static final long serialVersionUID = -296701588323249465L;

	private final Map<String, ProjectFile> any = new HashMap<String, ProjectFile>();

	@JsonAnyGetter
	public Map<String, ProjectFile> getMappings()
	{
		return this.any;
	}

	@JsonAnySetter
	public void setMapping(String name, ProjectFile value)
	{
		this.any.put(name, value);
	}
}
