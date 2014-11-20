/**************************************************************************
 * Copyright (c) 2014 Applied Visions, Inc. All Rights Reserved.
 * Author: Applied Visions, Inc. - Chris Ellsworth
 * Project: Code Dx
 * SubSystem: com.secdec.codedx.model.x
 * FileName: MappingsRequest.java
 *************************************************************************/
package com.secdec.codedx.api.client;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;

/**
 * @author Chris Ellsworth
 *
 */
class GetMappingsRequest implements Serializable
{

	private static final long serialVersionUID = 3484698818606060527L;
	
	private List<String> files;

	public List<String> getFiles()
	{
		return this.files;
	}

	public void setFiles(List<String> files)
	{
		this.files = files;
	}

	@Override
	public String toString()
	{
		return "MappingsRequest [files=" + this.files + "]";
	}

}
