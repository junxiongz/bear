package me.chris.bear.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.Optional;

/**
 * 注解辅助操作接口
 *
 * @author Christopher
 * @date 2023/11/8
 **/
public interface Aux {

    static Optional<String> getDescribe(AnnotatedElement ae){
        Describe describe = ae.getAnnotation(Describe.class);
        if (describe != null) {
            return Optional.of(describe.value());
        }
        return Optional.empty();
    }

    static Optional<String> getDescribe(Enum e){
        try {
            return getDescribe(e.getDeclaringClass().getField(e.name()));
        } catch (NoSuchFieldException ex) {
            throw new AssertionError();
        }
    }

    static boolean isDeprecated(AnnotatedElement ae) {
        return ae.getAnnotation(Deprecated.class) != null;
    }

    static <T extends Annotation> Optional<T> getAnnotation(AnnotatedElement ae, Class<T> annotation){
        return Optional.ofNullable(ae.getAnnotation(annotation));
    }
}
