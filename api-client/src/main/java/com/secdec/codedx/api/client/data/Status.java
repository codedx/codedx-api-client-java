/**************************************************************************
* Copyright (c) 2014 Applied Visions, Inc. All Rights Reserved.
* Author: Applied Visions, Inc. - Chris Ellsworth
* Project: Code Dx
* SubSystem: com.secdec.codedx.model.x
* FileName: Status.java
*************************************************************************/
package com.secdec.codedx.api.client.data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Chris Ellsworth
 *
 */
public class Status implements Serializable
{
	private static final long serialVersionUID = -8181810577246016748L;
	
	@JsonIgnore
	private String id;
	private String type;
	private String display;
	private Boolean settable;

	public String getId()
	{
		return this.id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getType()
	{
		return this.type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public String getDisplay()
	{
		return this.display;
	}

	public void setDisplay(String display)
	{
		this.display = display;
	}

	public Boolean getSettable()
	{
		return this.settable;
	}

	public void setSettable(Boolean settable)
	{
		this.settable = settable;
	}

	@Override
	public String toString() {
		return "Status [id=" + id + ", type=" + type + ", display=" + display
				+ ", settable=" + settable + "]";
	}
}
