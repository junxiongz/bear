package me.chris.bear.util.jackson;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.introspect.AnnotatedClass;
import com.fasterxml.jackson.databind.jsontype.*;
import com.fasterxml.jackson.databind.jsontype.impl.StdTypeResolverBuilder;
import com.fasterxml.jackson.databind.jsontype.impl.TypeIdResolverBase;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Sets;

import java.io.IOException;
import java.util.Collection;
import java.util.function.Function;

/**
 * @author Christopher
 * @date 2023/11/8
 **/
public class DynamicTypeResolverBuilder extends StdTypeResolverBuilder {

    private final Function<Object, String> typeIdGetter;
    private final BiMap<String, Class> types = HashBiMap.create();

    public <T> DynamicTypeResolverBuilder(
            Class<T> baseType,
            Function<T, String> typeIdGetter,
            String typeProperty,
            boolean typeIdVisible) {
        super(JsonTypeInfo.Id.CUSTOM, JsonTypeInfo.As.PROPERTY, typeProperty);
        this.typeIdGetter = (Function<Object, String>) typeIdGetter;
        _defaultImpl = baseType;
        _typeIdVisible = typeIdVisible;
    }

    public void registerModule(ObjectMapper mapper) {
        mapper.registerModule(
                new Module() {
                    @Override
                    public String getModuleName() {
                        return "DynamicType#" + _defaultImpl.getCanonicalName();
                    }

                    @Override
                    public Version version() {
                        return Version.unknownVersion();
                    }

                    @Override
                    public void setupModule(SetupContext context) {
                        context.appendAnnotationIntrospector(
                                new AnnotationIntrospector() {
                                    @Override
                                    public Version version() {
                                        return Version.unknownVersion();
                                    }

                                    @Override
                                    public TypeResolverBuilder<?> findTypeResolver(
                                            MapperConfig<?> config, AnnotatedClass ac, JavaType baseType) {
                                        return useForType(baseType) ? DynamicTypeResolverBuilder.this : null;
                                    }
                                });
                    }
                });
    }

    public void registerSubTypes(String name, Class clazz) {
        types.put(name, clazz);
    }

    @Override
    public TypeDeserializer buildTypeDeserializer(
            DeserializationConfig config, JavaType baseType, Collection<NamedType> subtypes) {
        return useForType(baseType) ? super.buildTypeDeserializer(config, baseType, subtypes) : null;
    }

    @Override
    public TypeSerializer buildTypeSerializer(
            SerializationConfig config, JavaType baseType, Collection<NamedType> subtypes) {
        return useForType(baseType) ? super.buildTypeSerializer(config, baseType, subtypes) : null;
    }

    boolean useForType(JavaType t) {
        return _defaultImpl.isAssignableFrom(t.getRawClass());
    }

    class DyanmicIdResolver extends TypeIdResolverBase {

        private final MapperConfig<?> config;

        public DyanmicIdResolver(MapperConfig<?> config, JavaType baseType) {
            super(baseType, config.getTypeFactory());
            this.config = config;
        }

        String idFromClass(Class<?> clazz) {
            return types.inverse().get(clazz);
        }

        @Override
        public String idFromValue(Object value) {
            String id = idFromClass(value.getClass());
            if (id == null && _defaultImpl.isAssignableFrom(value.getClass())) {
                id = typeIdGetter.apply(value);
            }
            return id;
        }

        @Override
        public String idFromValueAndType(Object value, Class<?> suggestedType) {
            if (value != null) {
                return idFromValue(value);
            }
            return idFromClass(suggestedType);
        }

        @Override
        public JavaType typeFromId(DatabindContext context, String id) throws IOException {
            Class type = types.get(id);
            if (type != null) {
                return config.constructType(type);
            }
            return null;
        }

        @Override
        public JsonTypeInfo.Id getMechanism() {
            return JsonTypeInfo.Id.CUSTOM;
        }

        @Override
        public String getDescForKnownTypeIds() {
            return Sets.newTreeSet(types.keySet()).toString();
        }
    }
}
