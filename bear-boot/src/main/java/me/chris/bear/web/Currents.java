package me.chris.bear.web;

import com.google.common.collect.ClassToInstanceMap;
import com.google.common.collect.MutableClassToInstanceMap;
import me.chris.bear.error.Errors;
import org.springframework.web.context.request.RequestAttributes;

import javax.annotation.Nonnull;
import java.util.Optional;

/**
 * @author Christopher
 * @date 2023/11/8
 **/
public interface Currents {

    @Nonnull
    static <T> T require(Class<T> type) {
        T t = (T) Holder.getCurrentMap().get(type);
        if (t == null) {
            throw Errors.unauthorized().asException();
        }
        return t;
    }

    @SuppressWarnings("unchecked")
    static <T> Optional<T> resolve(Class<T> type) {
        return Optional.ofNullable((T) Holder.getCurrentMap().get(type));
    }

    static <T> void put(Class<? super T> type, T o) {
        Holder.getCurrentMap().put(type, o);
    }

    final class Holder {

        private static final String NAME = "@Current";

        @SuppressWarnings("unchecked")
        private static ClassToInstanceMap<Object> getCurrentMap() {
            RequestAttributes attributes = SpringWeb.currentRequestAttributes();
            ClassToInstanceMap<Object> map = (MutableClassToInstanceMap<Object>) attributes.getAttribute(NAME, RequestAttributes.SCOPE_REQUEST);
            if (map == null) {
                map = MutableClassToInstanceMap.create();
                attributes.setAttribute(NAME, map, RequestAttributes.SCOPE_REQUEST);
            }
            return map;
        }
    }
}
