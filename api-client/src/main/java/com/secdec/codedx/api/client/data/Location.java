/**************************************************************************
* Copyright (c) 2014 Applied Visions, Inc. All Rights Reserved.
* Author: Applied Visions, Inc. - Chris Ellsworth
* Project: Code Dx
* SubSystem: com.secdec.codedx.model.x
* FileName: Location.java
*************************************************************************/
package com.secdec.codedx.api.client.data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Chris Ellsworth
 *
 */
public class Location implements Serializable
{
	
	private static final long serialVersionUID = 3892227143901971869L;
	
	@JsonProperty("short")
	private String fileName;
	private String path;
	private Long line;
	private Long lineStart;
	private Long lineEnd;
	private Boolean hasSource;

	public String getFileName()
	{
		return this.fileName;
	}

	public void setFileName(String fileName)
	{
		this.fileName = fileName;
	}

	public String getPath()
	{
		return this.path;
	}

	public void setPath(String path)
	{
		this.path = path;
	}

	public Long getLine()
	{
		return this.line;
	}

	public void setLine(Long line)
	{
		this.line = line;
	}

	public Long getLineStart()
	{
		return this.lineStart;
	}

	public void setLineStart(Long lineStart)
	{
		this.lineStart = lineStart;
	}

	public Long getLineEnd()
	{
		return this.lineEnd;
	}

	public void setLineEnd(Long lineEnd)
	{
		this.lineEnd = lineEnd;
	}

	public Boolean getHasSource()
	{
		return this.hasSource;
	}

	public void setHasSource(Boolean hasSource)
	{
		this.hasSource = hasSource;
	}

	@Override
	public String toString()
	{
		return "Location [fileName=" + this.fileName + ", path=" + this.path + ", line=" + this.line + ", lineStart="
				+ this.lineStart + ", lineEnd=" + this.lineEnd + ", hasSource=" + this.hasSource + "]";
	}

}
