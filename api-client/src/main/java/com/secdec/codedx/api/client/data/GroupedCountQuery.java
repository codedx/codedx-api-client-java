package com.secdec.codedx.api.client.data;

import java.io.Serializable;

public class GroupedCountQuery implements Serializable{

	private static final long serialVersionUID = 3355988342390644458L;
	
	private Filter filter;
	private String countBy;
	
	public Filter getFilter()
	{
		return this.filter;
	}

	public void setFilter(Filter filter)
	{
		this.filter = filter;
	}

	public String getCountBy() {
		return countBy;
	}

	public void setCountBy(String countBy) {
		this.countBy = countBy;
	}
}
