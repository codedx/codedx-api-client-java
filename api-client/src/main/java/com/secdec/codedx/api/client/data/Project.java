/**************************************************************************
* Copyright (c) 2014 Applied Visions, Inc. All Rights Reserved.
* Author: Applied Visions, Inc. - Chris Ellsworth
* Project: Code Dx
* SubSystem: com.secdec.codedx.model.api
* FileName: Project.java
*************************************************************************/
package com.secdec.codedx.api.client.data;

import java.io.Serializable;
import java.util.List;

public class Project implements Serializable
{

	private static final long serialVersionUID = -1431393024142420463L;
	
	private Long id;
	private String name;
	private AnalysisRun[] analysisRuns;

	public Long getId()
	{
		return this.id;
	}

	public void setId(Long id)
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

	@Deprecated
	public AnalysisRun[] getAnalysisRuns()
	{
		return this.analysisRuns;
	}

	public void setAnalysisRuns(AnalysisRun[] analysisRuns)
	{
		this.analysisRuns = analysisRuns;
	}

	@Override
	public String toString()
	{
		return "Project [id=" + this.id + ", name=" + this.name + ", analysisRuns="
				+ this.analysisRuns + "]";
	}

	@Deprecated
	public Long getLatestRunId()
	{
		AnalysisRun[] runs = getAnalysisRuns();
		if (runs != null && runs.length > 0)
		{
			Long runId = runs[runs.length - 1].getId();
			return runId;
		}
		else
		{
			return null;
		}
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Project other = (Project) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
