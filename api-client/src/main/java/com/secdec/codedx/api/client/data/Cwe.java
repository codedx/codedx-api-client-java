/**************************************************************************
* Copyright (c) 2014 Applied Visions, Inc. All Rights Reserved.
* Author: Applied Visions, Inc. - Chris Ellsworth
* Project: Code Dx
* SubSystem: com.secdec.codedx.model.x
* FileName: Cwe.java
*************************************************************************/
package com.secdec.codedx.api.client.data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;

/**
 * @author Chris Ellsworth
 *
 */
public class Cwe implements Serializable
{

	private static final long serialVersionUID = -3251725522307800455L;

	public static final String NO_CWE_ID = "none";

	private Object id;
	private String name;

	/**
	 * @return the id
	 */
	public Object getId()
	{
		return this.id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Object id)
	{
		this.id = id;
	}

	public String getName()
	{
		return this.name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	@Override
	public String toString()
	{
		return "Cwe [id=" + this.id + ", name=" + this.name + "]";
	}
	
	
}
