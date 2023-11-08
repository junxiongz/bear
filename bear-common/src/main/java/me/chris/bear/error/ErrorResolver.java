package me.chris.bear.error;

import java.util.function.Function;

/**
 * @author Christopher
 * @date 2023/11/8
 **/
public interface ErrorResolver extends Function<Throwable,ErrorDetailException> {
}
