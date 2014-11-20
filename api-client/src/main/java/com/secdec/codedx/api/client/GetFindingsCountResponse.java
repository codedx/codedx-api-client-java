/**************************************************************************
* Copyright (c) 2014 Applied Visions, Inc. All Rights Reserved.
* Author: Applied Visions, Inc. - Chris Ellsworth
* Project: Code Dx
* SubSystem: com.secdec.codedx.model.x
* FileName: Count.java
*************************************************************************/
package com.secdec.codedx.api.client;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;

/**
 * @author Chris Ellsworth
 *
 */
class GetFindingsCountResponse implements Serializable
{
	
	private static final long serialVersionUID = -1607958927503778611L;
	
	private Long count;

	public Long getCount()
	{
		return this.count;
	}

	public void setCount(Long count)
	{
		this.count = count;
	}
}
