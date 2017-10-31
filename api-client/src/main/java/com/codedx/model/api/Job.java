/**************************************************************************
 * Copyright (c) 2014 Applied Visions, Inc. All Rights Reserved.
 * Author: Applied Visions, Inc. - Chris Ellsworth
 * Project: Code Dx
 * SubSystem: com.secdec.codedx.model.api
 * FileName: Job.java
 *************************************************************************/
package com.codedx.model.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Job implements Serializable {
	public static final String COMPLETED = "completed";
	public static final String FAILED = "failed";
	public static final String QUEUED = "queued";
	public static final String RUNNING = "running";

	private static final long serialVersionUID = 5729574902925620649L;

	private final String jobId;
	private final String status;

	public String getJobId() {
		return this.jobId;
	}

	public String getStatus() {
		return this.status;
	}

	@JsonCreator
	public Job(@JsonProperty("jobId")String jobId, @JsonProperty("status")String status){
		this.jobId = jobId;
		this.status = status;
	}

	@Override
	public String toString() {
		return "Job{" +
				"jobId='" + jobId + '\'' +
				", status='" + status + '\'' +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Job)) return false;

		Job job = (Job) o;

		if (getJobId() != null ? !getJobId().equals(job.getJobId()) : job.getJobId() != null) return false;
		return getStatus() != null ? getStatus().equals(job.getStatus()) : job.getStatus() == null;
	}

	@Override
	public int hashCode() {
		int result = getJobId() != null ? getJobId().hashCode() : 0;
		result = 31 * result + (getStatus() != null ? getStatus().hashCode() : 0);
		return result;
	}
}
