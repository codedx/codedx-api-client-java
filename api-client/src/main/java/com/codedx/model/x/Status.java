/**************************************************************************
 * Copyright (c) 2014 Applied Visions, Inc. All Rights Reserved.
 * Author: Applied Visions, Inc. - Chris Ellsworth
 * Project: Code Dx
 * SubSystem: com.secdec.codedx.model.x
 * FileName: Status.java
 *************************************************************************/
package com.codedx.model.x;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * @author Chris Ellsworth
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Status implements Serializable, Comparable<Status> {
	// display values
	public static final String IGNORED = "Ignored";
	public static final String FALSE_POSITIVE = "False Positive";
	public static final String FIXED = "Fixed";
	public static final String GONE = "Gone";
	public static final String MITIGATED = "Mitigated";
	// type values
	public static final String USER = "user";
	public static final String ASSIGNED = "assigned";
	public static final String[] COMPLETED_STATUSES = new String[]{IGNORED, FALSE_POSITIVE, FIXED, GONE, MITIGATED};
	private static final long serialVersionUID = 7612752081519450919L;

	private final String id;
	private final String type;
	private final String display;
	private final Boolean settable;
	private final int order;
	private final int group;

	public String getId() {
		return this.id;
	}

	public String getType() {
		return this.type;
	}

	public String getDisplay() {
		return this.display;
	}

	public Boolean getSettable() {
		return this.settable;
	}

	public int getOrder() {
		return order;
	}

	public int getGroup() {
		return group;
	}


	@JsonCreator
	public Status(@JsonProperty("id") String id,
				  @JsonProperty("type") String type,
				  @JsonProperty("display") String display,
				  @JsonProperty("settable") Boolean settable,
				  @JsonProperty("order") int order,
				  @JsonProperty("group") int group){
		this.id = id;
		this.type = type;
		this.display = display;
		this.settable = settable;
		this.order = order;
		this.group = group;
	}


	@Override
	public String toString() {
		return "Status [id=" + this.id + ", type=" + this.type + ", display=" + this.display + ", settable=" + this.settable + ", group="
				+ this.group + ", order=" + this.order + "]";
	}

	@Override
	public int compareTo(Status that) {
		if (that == null) {
			return 1;
		} else if (this.group == that.group) {
			return this.order - that.order;
		} else {
			return this.group - that.group;
		}
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Status)) return false;

		Status status = (Status) o;

		if (getOrder() != status.getOrder()) return false;
		if (getGroup() != status.getGroup()) return false;
		if (!getId().equals(status.getId())) return false;
		if (!getType().equals(status.getType())) return false;
		if (!getDisplay().equals(status.getDisplay())) return false;
		return getSettable().equals(status.getSettable());
	}

	@Override
	public int hashCode() {
		int result = getId().hashCode();
		result = 31 * result + getType().hashCode();
		result = 31 * result + getDisplay().hashCode();
		result = 31 * result + getSettable().hashCode();
		result = 31 * result + getOrder();
		result = 31 * result + getGroup();
		return result;
	}
}
