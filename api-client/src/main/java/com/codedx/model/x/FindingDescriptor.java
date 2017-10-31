package com.codedx.model.x;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FindingDescriptor implements Serializable {

	private static final long serialVersionUID = 609746162349126059L;

	private final int id;
	private final String code;
	private final String name;
	private final String descriptorType;
	private final List<String> hierarchy;

	public int getId() { return this.id; }
	public String getCode() { return this.code; }
	public String getName() { return this.name; }
	public String getDescriptorType() { return this.descriptorType; }
	public List<String> getHierarchy() { return this.hierarchy; }

	@JsonCreator
	public FindingDescriptor(@JsonProperty("id")int id,
							 @JsonProperty("code") String code,
							 @JsonProperty("name") String name,
							 @JsonProperty("type") String descriptorType,
							 @JsonProperty("hierarchy") List<String> hierarchy){
		this.id = id;
		this.code = code;
		this.name = name;
		this.descriptorType = descriptorType;
		this.hierarchy = hierarchy;
	}

	@Override
	public String toString() {
		return "FindingDescriptor{" +
				"id=" + id +
				", code='" + code + '\'' +
				", name='" + name + '\'' +
				", descriptorType='" + descriptorType + '\'' +
				", hierarchy=" + hierarchy +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof FindingDescriptor)) return false;

		FindingDescriptor that = (FindingDescriptor) o;

		if (getId() != that.getId()) return false;
		if (getCode() != null ? !getCode().equals(that.getCode()) : that.getCode() != null) return false;
		if (getName() != null ? !getName().equals(that.getName()) : that.getName() != null) return false;
		if (getDescriptorType() != null ? !getDescriptorType().equals(that.getDescriptorType()) : that.getDescriptorType() != null)
			return false;
		return getHierarchy() != null ? getHierarchy().equals(that.getHierarchy()) : that.getHierarchy() == null;
	}

	@Override
	public int hashCode() {
		int result = getId();
		result = 31 * result + (getCode() != null ? getCode().hashCode() : 0);
		result = 31 * result + (getName() != null ? getName().hashCode() : 0);
		result = 31 * result + (getDescriptorType() != null ? getDescriptorType().hashCode() : 0);
		result = 31 * result + (getHierarchy() != null ? getHierarchy().hashCode() : 0);
		return result;
	}
}
