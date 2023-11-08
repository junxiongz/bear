package me.chris.bear.mapper;

import me.chris.bear.error.Errors;
import me.chris.bear.util.Lambdas;
import org.mapstruct.TargetType;

import java.util.Collection;
import java.util.Map;

/**
 * <ul>
 *   <li>实现 IdNameDTO 的基本转换
 *   <li>实现枚举的基本转换
 *   <li>数字与布尔值互转
 *   <li>集合转换为数字
 * </ul>
 *
 * @author Christopher
 * @date 2023/11/8
 **/
public interface BaseMapper {

    static Integer booleanToInteger(Boolean s) {
        return Lambdas.apply(s, v -> v ? 1 : 0);
    }

    static Boolean integerToBoolean(Integer s) {
        return Lambdas.apply(s, v -> v != 0);
    }

    static <T extends Enum<T>> Integer enumToOrdinal(T s) {
        return Lambdas.apply(s, Enum::ordinal);
    }

    static <T extends Enum<T>> T ordinalToEnum(Integer s, @TargetType Class<T> type) {
        return Lambdas.apply(
                s,
                v -> {
                    Errors.badRequest().check(type.getEnumConstants().length > v, "Invalid ordinal");
                    return type.getEnumConstants()[v];
                });
    }

    static <T extends Enum<T>> String enumToName(T s) {
        return Lambdas.apply(s, Enum::name);
    }

    static <T extends Enum<T>> T nameToEnum(String s, @TargetType Class<T> type) {
        return Lambdas.apply(s, v -> Enum.valueOf(type, v));
    }

    static <T extends Collection> Integer collectionToSize(T s) {
        return Lambdas.apply(s, Collection::size);
    }

    static <T extends Map> Integer mapToSize(T s) {
        return Lambdas.apply(s, Map::size);
    }
}
