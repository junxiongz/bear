package me.chris.bear.data.repo;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import me.chris.bear.data.querydsl.IsPredicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import java.io.Serializable;
import java.util.List;

/**
 * @Author Christopher
 * @Since 2023/11/16 2:27 PM
 */
@NoRepositoryBean
public interface BaseQueryRepository<T, PK extends Serializable>
        extends BaseRepository<T, PK>,
        PagingAndSortingRepository<T, PK>,
        QuerydslPredicateExecutor<T>,
        QueryByExampleExecutor<T> {

    /** {@inheritDoc} */
    @Override
    List<T> findAll(Sort sort);

    // endregion

    // region QueryDSL List

    /** {@inheritDoc} */
    @Override
    List<T> findAll(Predicate predicate);
    /** {@inheritDoc} */
    @Override
    List<T> findAll(Predicate predicate, Sort sort);
    /** {@inheritDoc} */
    @Override
    List<T> findAll(Predicate predicate, OrderSpecifier<?>... orders);
    /** {@inheritDoc} */
    @Override
    List<T> findAll(OrderSpecifier<?>... orders);

    // endregion
    default List<T> listAll(Predicate predicate, Pageable pageable) {
        // FIXME avoid count
        return findAll(predicate, pageable).getContent();
    }

    // region IsPredicate
    default List<T> findAll(IsPredicate predicate) {
        if (predicate == null) {
            return findAll();
        }
        return findAll(predicate.toPredicate());
    }

    default Page<T> findAll(IsPredicate predicate, Pageable pageable) {
        if (predicate == null) {
            return findAll(pageable);
        }
        return findAll(predicate.toPredicate(), pageable);
    }
}
