package com.secdec.codedx.api.client.data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;

public class CreateAnalysisRunResult implements Serializable{

	private static final long serialVersionUID = 324805913123181185L;
	
	private String jobId;
	private Long runId;

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public Long getRunId() {
		return runId;
	}

	public void setRunId(Long runId) {
		this.runId = runId;
	}
	
	@Override
	public String toString() {
		return "CreateAnalysisRunResponse [jobId=" + jobId + ", runId=" + runId
				+ "]";
	}
}
