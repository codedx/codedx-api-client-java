package com.codedx.model.x;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LocationRange implements Serializable {

	private static final long serialVersionUID = 340597574695697007L;

	public final int start;
	public final Integer end;

	public Integer getEffectiveEnd() {
		if(this.end == null){
			return this.start;
		} else {
			return this.end.intValue();
		}
	}

	public int getStart() {
		return this.start;
	}

	@JsonCreator
	public LocationRange(@JsonProperty("start")int start, @JsonProperty("end")Integer end){
		this.start = start;
		this.end = end;
	}

	@Override
	public String toString() {
		return "LocationRange{" +
				"start=" + start +
				", end=" + end +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof LocationRange)) return false;

		LocationRange that = (LocationRange) o;

		if (getStart() != that.getStart()) return false;
		return end != null ? end.equals(that.end) : that.end == null;
	}

	@Override
	public int hashCode() {
		int result = getStart();
		result = 31 * result + (end != null ? end.hashCode() : 0);
		return result;
	}
}
