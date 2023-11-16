package me.chris.bear.data.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.List;

/**
 * @Author Christopher
 * @Since 2023/11/16 2:25 PM
 */
@NoRepositoryBean
public interface BaseRepository<T, PK extends Serializable> extends CrudRepository<T, PK> {
    List<T> findAll();
    /** {@inheritDoc} */
    List<T> findAllById(Iterable<PK> ids);
}
