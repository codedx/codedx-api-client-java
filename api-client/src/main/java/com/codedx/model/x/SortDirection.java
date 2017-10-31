/**************************************************************************
 * Copyright (c) 2014 Applied Visions, Inc. All Rights Reserved.
 * Author: Applied Visions, Inc. - Chris Ellsworth
 * Project: Code Dx
 * SubSystem: com.secdec.codedx.model.x
 * FileName: SortDirection.java
 *************************************************************************/
package com.codedx.model.x;

/**
 * @author Chris Ellsworth
 */
public enum SortDirection {
	ASCENDING("ascending"), DESCENDING("descending");

	private String key;

	SortDirection(String key) {
		this.key = key;
	}

	public String getKey() {
		return this.key;
	}
}
