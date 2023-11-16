package me.chris.bear.data.entity;

import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Transient;
import org.springframework.data.domain.Persistable;

/**
 * @author Christopher
 * @date 2023/11/15
 **/
@MappedSuperclass
public abstract class AbstractPersistableIdLess<T> implements Persistable<T> {

    @Transient
    public boolean isNew() {
        return null == getId();
    }

    @Override
    public String toString() {
        return String.format("Entity of type %s with id: %s", this.getClass().getName(), getId());
    }

}
