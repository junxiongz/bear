package me.chris.bear.util.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import me.chris.bear.error.Errors;
import me.chris.bear.util.JSON;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.Map;

/**
 * @author Christopher
 * @date 2023/11/8
 **/
public interface ObjectMapperHelper {

    ObjectMapper mapper();
    /** @return Check is this string represent a JSON object */
    default boolean isValidObject(String json) {
        if (json == null || json.length() < 2) {
            return false;
        }
        try {
            JsonNode node = mapper().readTree(json);
            return node.isObject();
        } catch (IOException e) {
            // ignored
        }
        return false;
    }

    /** @return null for invalid json */
    @Nullable
    default JsonNodeType getJsonType(String json) {
        if (Strings.isNullOrEmpty(json)) {
            return null;
        }
        try {
            JsonNode node = mapper().readTree(json);
            return node.getNodeType();
        } catch (IOException e) {
            // ignored
        }
        return null;
    }
    /** Parse the JSON to map */
    default Map<String, Object> toMap(String json) {
        if (Strings.isNullOrEmpty(json)) {
            return Maps.newHashMap();
        }
        try {
            return mapper().readValue(json, JSON.getTypeReferenceOfMapStringObject());
        } catch (IOException e) {
            throw Errors.badJson().asException(e, "failed to convert json to map");
        }
    }

    default Map<String, Object> toMap(byte[] json) {
        if (json == null || json.length == 0) {
            return Maps.newHashMap();
        }
        try {
            return mapper().readValue(json, JSON.getTypeReferenceOfMapStringObject());
        } catch (IOException e) {
            throw Errors.badJson().asException(e, "failed to convert json to map");
        }
    }

    /** Convert the source to map */
    default Map<String, Object> toMap(Object source) {
        return mapper().convertValue(source, JSON.getTypeReferenceOfMapStringObject());
    }

    /** convert the source object to given type */
    default <T> T toObject(Object source, Class<T> type) {
        return mapper().convertValue(source, type);
    }

    /** Update the target object by given source */
    default <T> T update(Object source, T target) {
        if (source == null) {
            return target;
        }
        try {
            return mapper().updateValue(target, source);
        } catch (JsonMappingException e) {
            throw Errors.badJson().asException(e, "failed to update source to target");
        }
    }

    /** Update the target object by given json */
    default <T> T update(String json, T target) {
        if (Strings.isNullOrEmpty(json)) {
            return target;
        }
        try {
            return mapper().readerForUpdating(target).readValue(json);
        } catch (IOException e) {
            throw Errors.badJson().asException(e, "failed to update json source to target");
        }
    }



    /** Serialize object to string */
    default String stringify(Object o) {
        return stringify(o, false);
    }

    /** Serialize object to bytes */
    default byte[] bytify(Object o) {
        return bytify(o, false);
    }

    default byte[] bytify(Object o, boolean pretty) {
        try {
            if (pretty) {
                return mapper().writerWithDefaultPrettyPrinter().writeValueAsBytes(o);
            } else {
                return mapper().writeValueAsBytes(o);
            }
        } catch (JsonProcessingException e) {
            throw Errors.badJson().asException(e, "failed to bytify json: %s", e.getMessage());
        }
    }

    default String stringify(Object o, boolean pretty) {
        try {
            if (pretty) {
                return mapper().writerWithDefaultPrettyPrinter().writeValueAsString(o);
            } else {
                return mapper().writeValueAsString(o);
            }
        } catch (JsonProcessingException e) {
            throw Errors.badJson().asException(e, "failed to stringify json: %s", e.getMessage());
        }
    }
    /** Deserialize the json to type */
    default <T> T parse(String json, Class<T> type) {
        if (Strings.isNullOrEmpty(json)) {
            return null;
        }
        try {
            return mapper().readValue(json, type);
        } catch (IOException e) {
            throw Errors.badJson().asException(e, "failed to parse json: %s", e.getMessage());
        }
    }
    /** Deserialize the bytes to type */
    default <T> T parse(byte[] json, Class<T> type) {
        if (json == null || json.length == 0) {
            return null;
        }
        try {
            return mapper().readValue(json, type);
        } catch (IOException e) {
            throw Errors.badJson().asException(e, "failed to parse json bytes: %s", e.getMessage());
        }
    }
}
