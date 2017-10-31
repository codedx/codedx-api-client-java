package com.codedx.model.x;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DetectionMethod implements Serializable {
    private static final long serialVersionUID = 1834198419;

    private final int id;
    private final String name;
    private final boolean readOnly;

    public int getId() { return id; }
    public String getName() { return name; }
    public boolean isReadOnly() { return readOnly; }

    @JsonCreator
    public DetectionMethod(@JsonProperty("id") int id, @JsonProperty("name") String name, @JsonProperty("readOnly") boolean readOnly){
        this.id = id;
        this.name = name;
        this.readOnly = readOnly;
    }

    @Override
    public String toString() {
        return "DetectionMethod{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", readOnly=" + readOnly +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DetectionMethod)) return false;

        DetectionMethod that = (DetectionMethod) o;

        if (getId() != that.getId()) return false;
        if (isReadOnly() != that.isReadOnly()) return false;
        return getName() != null ? getName().equals(that.getName()) : that.getName() == null;
    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (isReadOnly() ? 1 : 0);
        return result;
    }
}
