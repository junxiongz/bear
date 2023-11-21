package me.chris.bear.data.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.io.Serializable;

/**
 * @Author Christopher
 * @Since 2023/11/16 2:26 PM
 */
public interface BaseJpaRepository<T, PK extends Serializable>
        extends BaseQueryRepository<T, PK>, JpaRepository<T, PK>, JpaSpecificationExecutor<T> {

}
