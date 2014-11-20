package com.secdec.codedx.api.client;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.secdec.codedx.api.client.data.Filter;

@JsonInclude(Include.NON_NULL)
class GetFindingsCountRequest implements Serializable {
	
	private static final long serialVersionUID = -5386399369897401612L;
	
	private Filter filter;

	public Filter getFilter()
	{
		return this.filter;
	}

	public void setFilter(Filter filter)
	{
		this.filter = filter;
	}
	
	@Override
	public String toString() {
		return "CountQuery [filter=" + filter + "]";
	}
	
}
