package com.codedx.model.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateAnalysisRunResponse implements Serializable {

	private static final long serialVersionUID = 2066574874669234203L;
	private final int id;
	private final String jobId;

	public String getJobId() {
		return this.jobId;
	}

	public int getRunId() {
		return this.id;
	}

	@JsonCreator
	public CreateAnalysisRunResponse(@JsonProperty("analysisId") int id, @JsonProperty("jobId") String jobId){
		this.id = id;
		this.jobId = jobId;
	}

	@Override
	public String toString() {
		return "CreateAnalysisRunResponse{" +
				"id=" + id +
				", jobId='" + jobId + '\'' +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof CreateAnalysisRunResponse)) return false;

		CreateAnalysisRunResponse that = (CreateAnalysisRunResponse) o;

		if (id != that.id) return false;
		return getJobId() != null ? getJobId().equals(that.getJobId()) : that.getJobId() == null;
	}

	@Override
	public int hashCode() {
		int result = id;
		result = 31 * result + (getJobId() != null ? getJobId().hashCode() : 0);
		return result;
	}
}
