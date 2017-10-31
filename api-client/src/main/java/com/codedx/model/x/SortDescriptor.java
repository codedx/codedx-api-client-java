/**************************************************************************
 * Copyright (c) 2014 Applied Visions, Inc. All Rights Reserved.
 * Author: Applied Visions, Inc. - Chris Ellsworth
 * Project: Code Dx
 * SubSystem: com.secdec.codedx.model.x
 * FileName: SortDescriptor.java
 *************************************************************************/
package com.codedx.model.x;

/**
 * @author Chris Ellsworth
 */
public enum SortDescriptor {
	ID("id"), RULE("rule"), SEVERITY("severity"), PATH("path"), STATUS("status"), CWE("cwe"), TOOL("tool");

	private String key;

	SortDescriptor(String key) {
		this.key = key;
	}

	public String getKey() {
		return this.key;
	}
}
