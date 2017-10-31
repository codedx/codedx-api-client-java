/**************************************************************************
 * Copyright (c) 2014 Applied Visions, Inc. All Rights Reserved.
 * Author: Applied Visions, Inc. - Chris Ellsworth
 * Project: Code Dx
 * SubSystem: com.secdec.codedx.model.x
 * FileName: GroupedCount.java
 *************************************************************************/
package com.codedx.model.x;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

/**
 * @author Chris Ellsworth
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GroupedCount implements Serializable {
	private static final long serialVersionUID = 1653058077921897956L;

	private final String id;
	private final boolean isSynthetic;
	private final String name;
	private Integer rank;
	private final int count;
	private List<Object> children;

	public String getId() {
		return this.id;
	}

	public boolean isSynthetic() {
		return this.isSynthetic;
	}

	public String getName() {
		return this.name;
	}

	public Integer getRank() {
		return this.rank;
	}

	public void setRank(Integer rank){
		this.rank = rank;
	}

	public int getCount() {
		return this.count;
	}

	public List<Object> getChildren() {
		return this.children;
	}

	public void setChildren(List<Object> children) {
		this.children = children;
	}

	@JsonCreator
	public GroupedCount(@JsonProperty("id")String id,
						@JsonProperty("isSynthetic")boolean isSynthetic,
						@JsonProperty("name")String name,
						@JsonProperty("count")int count) {
		this.id = id;
		this.isSynthetic = isSynthetic;
		this.name = name;
		this.count = count;
	}

	@Override
	public String toString() {
		return "GroupedCount{" +
				"id='" + id + '\'' +
				", isSynthetic=" + isSynthetic +
				", name='" + name + '\'' +
				", rank=" + rank +
				", count=" + count +
				", children=" + children +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof GroupedCount)) return false;

		GroupedCount that = (GroupedCount) o;

		if (isSynthetic() != that.isSynthetic()) return false;
		if (getCount() != that.getCount()) return false;
		if (getId() != null ? !getId().equals(that.getId()) : that.getId() != null) return false;
		if (getName() != null ? !getName().equals(that.getName()) : that.getName() != null) return false;
		if (getRank() != null ? !getRank().equals(that.getRank()) : that.getRank() != null) return false;
		return getChildren() != null ? getChildren().equals(that.getChildren()) : that.getChildren() == null;
	}

	@Override
	public int hashCode() {
		int result = getId() != null ? getId().hashCode() : 0;
		result = 31 * result + (isSynthetic() ? 1 : 0);
		result = 31 * result + (getName() != null ? getName().hashCode() : 0);
		result = 31 * result + (getRank() != null ? getRank().hashCode() : 0);
		result = 31 * result + getCount();
		result = 31 * result + (getChildren() != null ? getChildren().hashCode() : 0);
		return result;
	}
}
