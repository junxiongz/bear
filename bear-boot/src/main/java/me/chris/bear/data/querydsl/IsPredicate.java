package me.chris.bear.data.querydsl;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Visitor;

import javax.annotation.Nullable;

/**
 * @Author Christopher
 * @Since 2023/11/16 2:28 PM
 */
public interface IsPredicate extends Predicate {

    Predicate toPredicate();

    @Override
    default Predicate not() {
        return toPredicate().not();
    }

    @Override
    @Nullable
    default <R, C> R accept(Visitor<R, C> v, @Nullable C context) {
        return toPredicate().accept(v, context);
    }

    @Override
    default Class<? extends Boolean> getType() {
        return toPredicate().getType();
    }
}
