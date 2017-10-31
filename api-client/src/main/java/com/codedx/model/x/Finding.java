/**************************************************************************
 * Copyright (c) 2014 Applied Visions, Inc. All Rights Reserved.
 * Author: Applied Visions, Inc. - Chris Ellsworth
 * Project: Code Dx
 * SubSystem: com.secdec.codedx.model.x
 * FileName: Finding.java
 *************************************************************************/
package com.codedx.model.x;

import com.codedx.model.api.Project;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


import java.io.Serializable;
import java.util.Comparator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * @author Chris Ellsworth
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Finding implements Serializable {
	private static final long serialVersionUID = 7628850382077521574L;

	public final static Comparator<Finding> DESCENDING_BY_SEVERITY = (left, right) -> {
		int leftValue = (left == null) ? 0 : left.getSeverityValue();
		int rightValue = (right == null) ? 0 : right.getSeverityValue();
		return rightValue - leftValue;
	};

	private final long id;
	private final DetectionMethod detectionMethod;
	private final FindingDescriptor descriptor;
	private final Location location;
	private final Severity severity;
	private final Cwe cwe;
	private final List<ToolResult> results;
	private String status;

	@JsonIgnore
	private Status richStatus;
	@JsonIgnore
	private Project project;

	private int getSeverityValue() {
		return this.severity.getKey();
	}

	public long getId() {
		return this.id;
	}

	public DetectionMethod getDetectionMethod() {
		return this.detectionMethod;
	}

	public Severity getSeverity() {
		return this.severity;
	}

	public FindingDescriptor getDescriptor() {
		return this.descriptor;
	}

	public List<ToolResult> getResults() {
		return results;
	}

	public Location getLocation() {
		return this.location;
	}

	public Cwe getCwe() {
		return this.cwe;
	}

	public String getStatus() {
		return this.status;
	}

	public Status getRichStatus() {
		return this.richStatus;
	}

	public Project getProject() {
		return this.project;
	}

	@JsonCreator
	public Finding(@JsonProperty("id")long id,
				   @JsonProperty("detectionMethod")DetectionMethod detectionMethod,
				   @JsonProperty("descriptor")FindingDescriptor descriptor,
				   @JsonProperty("location")Location location,
				   @JsonProperty("severity") Severity severity,
				   @JsonProperty("cwe")Cwe cwe,
				   @JsonProperty("status")String status,
				   @JsonProperty("results")List<ToolResult> results) {
		this.id = id;
		this.detectionMethod = detectionMethod;
		this.descriptor = descriptor;
		this.severity = severity;
		this.location = location;
		this.cwe = cwe;
		this.status = status;
		this.results = results;
	}

	public SortedSet<String> getToolNamesForDisplay() {
		SortedSet<String> names = new TreeSet<String>();
		List<ToolResult> toolResults = getResults();
		if (toolResults != null) {
			for (ToolResult tr : toolResults) {
				names.add(tr.getToolForDisplay());
			}
		}
		return names;
	}

	public String getPrimaryLocationType() {
		if (this.location != null) {
			return this.location.getPath().getPathType();
		} else {
			return null;
		}
	}

	@Override
	public String toString() {
		return "Finding{" +
				"id=" + id +
				", detectionMethod=" + detectionMethod +
				", descriptor=" + descriptor +
				", location=" + location +
				", severity=" + severity +
				", cwe=" + cwe +
				", results=" + results +
				", status='" + status + '\'' +
				", richStatus=" + richStatus +
				", project=" + project +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Finding)) return false;

		Finding finding = (Finding) o;

		if (getId() != finding.getId()) return false;
		if (getDetectionMethod() != null ? !getDetectionMethod().equals(finding.getDetectionMethod()) : finding.getDetectionMethod() != null)
			return false;
		if (getDescriptor() != null ? !getDescriptor().equals(finding.getDescriptor()) : finding.getDescriptor() != null)
			return false;
		if (getLocation() != null ? !getLocation().equals(finding.getLocation()) : finding.getLocation() != null)
			return false;
		if (getSeverity() != null ? !getSeverity().equals(finding.getSeverity()) : finding.getSeverity() != null)
			return false;
		if (getCwe() != null ? !getCwe().equals(finding.getCwe()) : finding.getCwe() != null) return false;
		if (getResults() != null ? !getResults().equals(finding.getResults()) : finding.getResults() != null)
			return false;
		if (getStatus() != null ? !getStatus().equals(finding.getStatus()) : finding.getStatus() != null) return false;
		if (getRichStatus() != null ? !getRichStatus().equals(finding.getRichStatus()) : finding.getRichStatus() != null)
			return false;
		return getProject() != null ? getProject().equals(finding.getProject()) : finding.getProject() == null;
	}

	@Override
	public int hashCode() {
		int result = (int) (getId() ^ (getId() >>> 32));
		result = 31 * result + (getDetectionMethod() != null ? getDetectionMethod().hashCode() : 0);
		result = 31 * result + (getDescriptor() != null ? getDescriptor().hashCode() : 0);
		result = 31 * result + (getLocation() != null ? getLocation().hashCode() : 0);
		result = 31 * result + (getSeverity() != null ? getSeverity().hashCode() : 0);
		result = 31 * result + (getCwe() != null ? getCwe().hashCode() : 0);
		result = 31 * result + (getResults() != null ? getResults().hashCode() : 0);
		result = 31 * result + (getStatus() != null ? getStatus().hashCode() : 0);
		result = 31 * result + (getRichStatus() != null ? getRichStatus().hashCode() : 0);
		result = 31 * result + (getProject() != null ? getProject().hashCode() : 0);
		return result;
	}
}
