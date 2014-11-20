package com.secdec.codedx.api.client.data;

import java.io.Serializable;

public class SystemInfo implements Serializable {

	private static final long serialVersionUID = 6074511202257424112L;
	
	
	private String version;
	private String coreVersion;
	private String editionVersion;
	private String editionDate;
	

	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getCoreVersion() {
		return coreVersion;
	}
	public void setCoreVersion(String coreVersion) {
		this.coreVersion = coreVersion;
	}
	public String getEditionVersion() {
		return editionVersion;
	}
	public void setEditionVersion(String editionVersion) {
		this.editionVersion = editionVersion;
	}
	public String getEditionDate() {
		return editionDate;
	}
	public void setEditionDate(String editionDate) {
		this.editionDate = editionDate;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	@Override
	public String toString() {
		return "SystemInfo [version=" + version + ", coreVersion="
				+ coreVersion + ", editionVersion=" + editionVersion
				+ ", editionDate=" + editionDate + "]";
	}

}
