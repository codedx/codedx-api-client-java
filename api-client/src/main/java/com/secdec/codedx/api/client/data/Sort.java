/**************************************************************************
* Copyright (c) 2014 Applied Visions, Inc. All Rights Reserved.
* Author: Applied Visions, Inc. - Chris Ellsworth
* Project: Code Dx
* SubSystem: com.secdec.codedx.model.x
* FileName: Sort.java
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
public class Sort implements Serializable
{

	private static final long serialVersionUID = 99053354703315851L;
	
	private String by;
	private String direction;

	public String getBy()
	{
		return this.by;
	}

	public void setBy(String by)
	{
		this.by = by;
	}

	public String getDirection()
	{
		return this.direction;
	}

	public void setDirection(String direction)
	{
		this.direction = direction;
	}

	@Override
	public String toString()
	{
		return "Sort [by=" + this.by + ", direction=" + this.direction + "]";
	}
}
