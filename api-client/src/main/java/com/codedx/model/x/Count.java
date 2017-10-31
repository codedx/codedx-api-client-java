/**************************************************************************
 * Copyright (c) 2014 Applied Visions, Inc. All Rights Reserved.
 * Author: Applied Visions, Inc. - Chris Ellsworth
 * Project: Code Dx
 * SubSystem: com.secdec.codedx.model.x
 * FileName: Count.java
 *************************************************************************/
package com.codedx.model.x;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;


/**
 * @author Chris Ellsworth
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Count implements Serializable {
	private static final long serialVersionUID = -8095186252361929252L;
	private final Long count;

	public Long getCount() {
		return this.count;
	}

	@JsonCreator
	public Count(@JsonProperty("count")Long count){
		this.count = count;
	}

	@Override
	public String toString() {
		return "Count{" +
				"count=" + count +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Count)) return false;

		Count count1 = (Count) o;

		return getCount() != null ? getCount().equals(count1.getCount()) : count1.getCount() == null;
	}

	@Override
	public int hashCode() {
		return getCount() != null ? getCount().hashCode() : 0;
	}
}
