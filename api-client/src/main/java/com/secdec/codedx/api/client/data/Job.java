/**************************************************************************
* Copyright (c) 2014 Applied Visions, Inc. All Rights Reserved.
* Author: Applied Visions, Inc. - Chris Ellsworth
* Project: Code Dx
* SubSystem: com.secdec.codedx.model.api
* FileName: Job.java
*************************************************************************/
package com.secdec.codedx.api.client.data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;

public class Job implements Serializable
{

	private static final long serialVersionUID = 1229322536363308491L;
		
	private String jobId;
	private String status;

	public String getJobId()
	{
		return this.jobId;
	}

	public void setJobId(String jobId)
	{
		this.jobId = jobId;
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
	public String toString()
	{
		return "Job [jobId=" + this.jobId + ", status=" + this.status + "]";
	}

}
