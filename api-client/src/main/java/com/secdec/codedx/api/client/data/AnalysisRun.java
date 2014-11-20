/**************************************************************************
* Copyright (c) 2014 Applied Visions, Inc. All Rights Reserved.
* Author: Applied Visions, Inc. - Chris Ellsworth
* Project: Code Dx
* SubSystem: com.secdec.codedx.model.x
* FileName: Run.java
*************************************************************************/
package com.secdec.codedx.api.client.data;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Chris Ellsworth
 *
 */
public class AnalysisRun implements Serializable
{

	private static final long serialVersionUID = 3672993767315256038L;
	
	private Long id;
	@JsonProperty("project")
	private Long projectId;
	private String date;
	private String inputDate;
	private Boolean canChangeStatuses;

	public Long getId()
	{
		return this.id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Long getProjectId()
	{
		return this.projectId;
	}

	public void setProjectId(Long projectId)
	{
		this.projectId = projectId;
	}

	public String getDate()
	{
		return this.date;
	}

	public void setDate(String date)
	{
		this.date = date;
	}

	public String getInputDate()
	{
		return this.inputDate;
	}

	public void setInputDate(String inputDate)
	{
		this.inputDate = inputDate;
	}

	public Boolean getCanChangeStatuses()
	{
		return this.canChangeStatuses;
	}

	public void setCanChangeStatuses(Boolean canChangeStatuses)
	{
		this.canChangeStatuses = canChangeStatuses;
	}

	@Override
	public String toString() {
		return "Run [id=" + id + ", projectId=" + projectId + ", date=" + date
				+ ", inputDate=" + inputDate + ", canChangeStatuses="
				+ canChangeStatuses + "]";
	}

	
}
