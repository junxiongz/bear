package me.chris.bear.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Maps;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.ToString;
import me.chris.bear.error.Errors;
import me.chris.bear.util.JSON;

import java.util.Map;
import java.util.Optional;

/**
 * @author Christopher
 * @date 2023/11/10
 **/
@ToString
@EqualsAndHashCode
public class PropertyObject {

    @JsonIgnore
    @Setter
    private Map<String, Object> properties;

    /**
     * convert map to this class
     */
    public static <T> T from(Map<String, Object> map, Class<T> type) {
        return JSON.toObject(map, type);
    }

    /**
     * parse json to this class
     */
    public static <T> T from(String json, Class<T> type) {
        return JSON.parse(json, type);
    }

    /**
     * @return all properties
     */
    @JsonAnyGetter
    public Map<String, Object> getProperties() {
        return properties;
    }

    /**
     * set the property
     */
    @JsonAnySetter
    public Object set(String name, Object value) {
        if (properties == null) {
            properties = Maps.newHashMap();
        }
        return properties.put(name, value);
    }

    /**
     * get value of the property
     */
    public Object get(String name) {
        if (properties != null) {
            return properties.get(name);
        }
        return null;
    }

    /**
     * @return properties contain this name
     */
    public boolean has(String name) {
        if (properties != null) {
            return properties.containsKey(name);
        }
        return false;
    }

    /**
     * clear all extra properties
     */
    public void clear() {
        if (properties != null) {
            properties.clear();
        }
    }

    /**
     * @return Map version of this object
     */
    public Map<String, Object> toMap() {
        return JSON.toMap(this);
    }

    /**
     * Convert to another type
     */
    public <T> T to(Class<T> type) {
        return JSON.toObject(this, type);
    }

    /**
     * Clone this object, return the same type
     */
    public PropertyObject copy() {
        return copy(getClass());
    }

    public <T> T copy(Class<T> type) {
        return from(toMap(), type);
    }

    /**
     * @return JSON serialized
     */
    public String stringify() {
        return JSON.stringify(this);
    }

    /**
     * cast this object to type
     */
    public <T> T requireType(Class<T> type) {
        return tryCast(type)
                .orElseThrow(
                        () -> Errors.badRequest().asException("need type %s but got %s", type, getClass()));
    }

    /**
     * try cast this object to type
     */
    public <T> Optional<T> tryCast(Class<T> type) {
        if (type.isAssignableFrom(getClass())) {
            return Optional.of(type.cast(this));
        }
        return Optional.empty();
    }
}
