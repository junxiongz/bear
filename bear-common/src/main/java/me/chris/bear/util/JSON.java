package me.chris.bear.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import me.chris.bear.util.jackson.DynamicTypeResolverBuilder;
import me.chris.bear.util.jackson.ObjectMapperHelper;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;

/**
 * JSON utils for Jackson
 *
 * @author Christopher
 * @date 2023/11/8
 **/
public interface JSON {


    /** Create a helper */
    static ObjectMapperHelper help(ObjectMapper mapper) {
        return () -> mapper;
    }

    /** @return Check is this string represent a JSON object */
    static boolean isValidObject(String json) {
        return helper().isValidObject(json);
    }

    /** @return null for invalid json */
    @Nullable
    static JsonNodeType getJsonType(String json) {
        return helper().getJsonType(json);
    }

    /** @see ObjectMapperHelper#toMap(String) */
    static Map<String, Object> toMap(String json) {
        return helper().toMap(json);
    }

    /** @see ObjectMapperHelper#toMap(Object) */
    static Map<String, Object> toMap(Object source) {
        return helper().toMap(source);
    }

    /** @see ObjectMapperHelper#toObject(Object,Class) */
    static <T> T toObject(Object source, Class<T> type) {
        return helper().toObject(source, type);
    }

    /** @see ObjectMapperHelper#update(Object,T) */
    static <T> T update(Object source, T target) {
        return helper().update(source, target);
    }

    /** @see ObjectMapperHelper#update(String,T) */
    static <T> T update(String json, T target) {
        return helper().update(json, target);
    }

    /** @see ObjectMapperHelper#stringify(Object) */
    static String stringify(Object o) {
        return helper().stringify(o, false);
    }

    /** @see ObjectMapperHelper#bytify(Object) */
    static byte[] bytify(Object o) {
        return helper().bytify(o, false);
    }

    /** @see ObjectMapperHelper#bytify(Object,boolean) */
    static byte[] bytify(Object o, boolean pretty) {
        return helper().bytify(o, pretty);
    }

    /** @see ObjectMapperHelper#stringify(Object,boolean) */
    static String stringify(Object o, boolean pretty) {
        return helper().stringify(o, pretty);
    }

    /** @see ObjectMapperHelper#parse(String, Class) */
    static <T> T parse(String json, Class<T> type) {
        return helper().parse(json, type);
    }

    /** @see ObjectMapperHelper#parse(byte[], Class) */
    static <T> T parse(byte[] json, Class<T> type) {
        return helper().parse(json, type);
    }

    /** Set to the new mapper */
    static ObjectMapper use(ObjectMapper mapper) {
        return Holder.HELPER.getAndSet(help(mapper)).mapper();
    }

    static ObjectMapperHelper use(ObjectMapperHelper helper) {
        return Holder.HELPER.getAndSet(helper);
    }

    static ObjectMapper mapper() {
        return helper().mapper();
    }

    static ObjectMapperHelper helper() {
        return Holder.HELPER.get();
    }

    static <T> void registerDynamicType(
            Class<T> base,
            Function<T, String> typeIdGetter,
            String typeIdProperty,
            boolean typeIdVisible) {
        Preconditions.checkArgument(
                !Holder.RESOLVER.containsKey(base), "type %s already registered as dynamic", base);
        Holder.RESOLVER.computeIfAbsent(
                base,
                baseType -> {
                    DynamicTypeResolverBuilder builder =
                            new DynamicTypeResolverBuilder(base, typeIdGetter, typeIdProperty, typeIdVisible);
                    builder.registerModule(mapper());
                    return builder;
                });
    }

    static <T> void registerDynamicSubtype(
            Class<T> baseType, String typeId, Class<? extends T> subtype) {
        Preconditions.checkNotNull(
                Holder.RESOLVER.get(baseType), "type %s not registered as dynamic", baseType)
                .registerSubTypes(typeId, subtype);
    }

    static TypeReference<Map<String, Object>> getTypeReferenceOfMapStringObject() {
        return Holder.TYPE_REF_MAP_STRING_OBJECT;
    }

    static TypeReference<Map<String, String>> getTypeReferenceOfMapStringString() {
        return Holder.TYPE_REF_MAP_STRING_STRING;
    }

    final class Holder {
        private static final AtomicReference<ObjectMapperHelper> HELPER =
                new AtomicReference<>(help(build()));
        private static final TypeReference<Map<String, Object>> TYPE_REF_MAP_STRING_OBJECT =
                new TypeReference<Map<String, Object>>() {};
        private static final TypeReference<Map<String, String>> TYPE_REF_MAP_STRING_STRING =
                new TypeReference<Map<String, String>>() {};

        // Only track current mapper
        private static final Map<Class<?>, DynamicTypeResolverBuilder> RESOLVER =
                Maps.newConcurrentMap();

        private static ObjectMapper build() {
            return new ObjectMapper()
                    .findAndRegisterModules()
                    .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                    .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
                    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        }
    }
}
