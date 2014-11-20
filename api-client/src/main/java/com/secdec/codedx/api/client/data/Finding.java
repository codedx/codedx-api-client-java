/**************************************************************************
 * Copyright (c) 2014 Applied Visions, Inc. All Rights Reserved.
 * Author: Applied Visions, Inc. - Chris Ellsworth
 * Project: Code Dx
 * SubSystem: com.secdec.codedx.model.x
 * FileName: Finding.java
 *************************************************************************/
package com.secdec.codedx.api.client.data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Chris Ellsworth
 *
 */
public class Finding implements Serializable
{

	private static final long serialVersionUID = -7618347213467320208L;
	
	private Long id;
	private String severity;
	private String tool;
	private String rule;
	private Location[] locations;
	private Cwe cwe;
	private String status;

	public Long getId()
	{
		return this.id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getSeverity()
	{
		return this.severity;
	}

	public void setSeverity(String severity)
	{
		this.severity = severity;
	}

	public String getTool()
	{
		return this.tool;
	}

	public void setTool(String tool)
	{
		this.tool = tool;
	}

	public String getRule()
	{
		return this.rule;
	}

	public void setRule(String rule)
	{
		this.rule = rule;
	}

	public Location[] getLocations()
	{
		return this.locations;
	}

	public void setLocations(Location[] locations)
	{
		this.locations = locations;
	}

	public Cwe getCwe()
	{
		return this.cwe;
	}

	public void setCwe(Cwe cwe)
	{
		this.cwe = cwe;
	}

	public String getStatus()
	{
		return this.status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (obj == null)
		{
			return false;
		}
		if (getClass() != obj.getClass())
		{
			return false;
		}
		Finding other = (Finding) obj;
		if (this.id == null)
		{
			if (other.id != null)
			{
				return false;
			}
		}
		else if (!this.id.equals(other.id))
		{
			return false;
		}
		return true;
	}
	
	@Override
	public String toString() {
		return "Finding [id=" + id + ", severity=" + severity + ", tool="
				+ tool + ", rule=" + rule + ", locations=" + locations
				+ ", cwe=" + cwe + ", status=" + status + "]";
	}
}
