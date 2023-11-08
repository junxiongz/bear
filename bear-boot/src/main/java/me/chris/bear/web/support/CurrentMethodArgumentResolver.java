package me.chris.bear.web.support;

import lombok.extern.slf4j.Slf4j;
import me.chris.bear.error.Errors;
import me.chris.bear.web.Current;
import me.chris.bear.web.Currents;
import org.springframework.core.MethodParameter;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.ValueConstants;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.annotation.AbstractNamedValueMethodArgumentResolver;

/**
 * @author Christopher
 * @date 2023/11/8
 **/
@Slf4j
public class CurrentMethodArgumentResolver extends AbstractNamedValueMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(Current.class);
    }

    @Override
    protected NamedValueInfo createNamedValueInfo(MethodParameter parameter) {
        Current ann = parameter.getParameterAnnotation(Current.class);
        Assert.notNull(ann, "No @Current");
        return new NamedValueInfo(
                parameter.getParameterType().getCanonicalName(),
                ann.required(),
                ValueConstants.DEFAULT_NONE);
    }

    @Override
    protected Object resolveName(String name, MethodParameter parameter, NativeWebRequest request) {
        return Currents.resolve(parameter.nestedIfOptional().getParameterType()).orElse(null);
    }

    @Override
    protected void handleMissingValue(String name, MethodParameter parameter) {
        log.error(
                "Required current {} not found for {}.{}",
                parameter.getParameterType().getSimpleName(),
                parameter.getMethod() == null
                        ? "UNKNOWN"
                        : parameter.getMethod().getDeclaringClass().getSimpleName(),
                parameter.getMethod() == null ? "UNKNOWN" : parameter.getMethod().getName());
        throw Errors.unauthorized().asException("缺少必要的授权信息");
    }
}
