package com.codedx.model.x;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ToolResult implements Serializable {
	private static final long serialVersionUID = -1359887106790850559L;

	private final int id;
	private final boolean isManual;
	private final DetectionMethod detectionMethod;
	private final String tool;
	private final Severity severity;
	private final List<String> toolHierarchy;
	private final FindingDescriptor descriptor;

	public int getId() {
		return this.id;
	}

	public boolean getIsManual() {
		return this.isManual;
	}

	public DetectionMethod getDetectionMethod() {
		return this.detectionMethod;
	}

	public String getTool() {
		return this.tool;
	}

	public Severity getSeverity() {
		return this.severity;
	}

	public List<String> getToolHierarchy(){
		return this.toolHierarchy;
	}

	public FindingDescriptor getDescriptor() {
		return this.descriptor;
	}

	public String getToolForDisplay(){
		if (this.isManual) {
			if (this.tool != null) {
				return this.tool + " (Manual Entry)";
			} else {
				return "Manual Entry";
			}
		}else {
			return this.tool;
		}
	}

	public List<String> getDescriptorHierarchyForDisplay() {
		if (this.isManual) {
			return Arrays.asList(getToolForDisplay(), this.descriptor.getName());
		} else if (this.descriptor != null) {
			return this.descriptor.getHierarchy() == null ? Arrays.asList("???") : this.descriptor.getHierarchy();
		} else {
			return Arrays.asList("???");
		}
	}

	@JsonCreator
	public ToolResult(@JsonProperty("id") int id,
					  @JsonProperty("isManual") boolean isManual,
					  @JsonProperty("detectionMethod")DetectionMethod detectionMethod,
					  @JsonProperty("tool") String tool,
					  @JsonProperty("severity") Severity severity,
					  @JsonProperty("toolHierarchy") List<String> toolHierarchy,
					  @JsonProperty("descriptor")FindingDescriptor descriptor) {
		this.id = id;
		this.isManual = isManual;
		this.detectionMethod = detectionMethod;
		this.tool = tool;
		this.severity = severity;
		this.toolHierarchy = toolHierarchy;
		this.descriptor = descriptor;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof ToolResult)) return false;

		ToolResult that = (ToolResult) o;

		if (getId() != that.getId()) return false;
		if (isManual != that.isManual) return false;
		if (!getDetectionMethod().equals(that.getDetectionMethod())) return false;
		if (!getTool().equals(that.getTool())) return false;
		if (!getSeverity().equals(that.getSeverity())) return false;
		if (!getToolHierarchy().equals(that.getToolHierarchy())) return false;
		return getDescriptor().equals(that.getDescriptor());
	}

	@Override
	public int hashCode() {
		int result = getId();
		result = 31 * result + (isManual ? 1 : 0);
		result = 31 * result + getDetectionMethod().hashCode();
		result = 31 * result + getTool().hashCode();
		result = 31 * result + getSeverity().hashCode();
		result = 31 * result + getToolHierarchy().hashCode();
		result = 31 * result + getDescriptor().hashCode();
		return result;
	}
}
