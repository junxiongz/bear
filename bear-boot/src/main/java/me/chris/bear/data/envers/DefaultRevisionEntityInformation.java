package me.chris.bear.data.envers;

import org.hibernate.envers.DefaultRevisionEntity;
import org.springframework.data.repository.history.support.RevisionEntityInformation;

/**
 * @Author Christopher
 * @Since 2023/11/16 11:33 AM
 */
public class DefaultRevisionEntityInformation implements RevisionEntityInformation {
    @Override
    public Class<?> getRevisionNumberType() {
        return Integer.class;
    }

    @Override
    public boolean isDefaultRevisionEntity() {
        return true;
    }

    @Override
    public Class<?> getRevisionEntityClass() {
        return DefaultRevisionEntity.class;
    }
}
