package me.chris.bear.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import me.chris.bear.util.JSON;
import org.mapstruct.Qualifier;
import org.mapstruct.TargetType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Christopher
 * @date 2023/11/8
 **/
public interface JsonMapper {

    @JsonMapping
    static <T> T fromJson(String s, @TargetType Class<T> type) {
        if (s == null) {
            return null;
        }
        return JSON.parse(s, type);
    }

    @JsonMapping
    static <T> String toJson(T s) {
        return JSON.stringify(s);
    }

    @JsonMapping
    static <T> T fromJsonNode(JsonNode s, @TargetType Class<T> type) {
        if (s == null) {
            return null;
        }
        try {
            return JSON.mapper().treeToValue(s, type);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("convert json node to value failed", e);
        }
    }

    @JsonMapping
    static <T> JsonNode toJsonNode(T s) {
        if (s == null) {
            return null;
        }
        return JSON.toObject(s, JsonNode.class);
    }

    @Qualifier
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.CLASS)
    @interface JsonMapping {}
}
