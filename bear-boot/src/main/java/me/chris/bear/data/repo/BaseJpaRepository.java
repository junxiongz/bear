package me.chris.bear.data.repo;

import me.chris.bear.error.Errors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.io.Serializable;
import java.util.Optional;

/**
 * @Author Christopher
 * @Since 2023/11/16 2:26 PM
 */
public interface BaseJpaRepository<T, PK extends Serializable>
        extends BaseQueryRepository<T, PK>, JpaRepository<T, PK>, JpaSpecificationExecutor<T> {

    Optional<T> findFirst();

    default T requireById(PK id) {
        return findById(id).orElseThrow(() -> Errors.notFound().asException("资源不存在"));
    }
}
