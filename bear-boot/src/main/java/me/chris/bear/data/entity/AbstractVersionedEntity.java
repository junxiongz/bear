package me.chris.bear.data.entity;

import com.querydsl.core.annotations.QuerySupertype;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author Christopher
 * @date 2023/11/15
 **/
@MappedSuperclass
@QuerySupertype
@Setter
@Getter
public class AbstractVersionedEntity<PK extends Serializable> extends AbstractEntity<PK> {

    @Version
    @Column(name = "version")
    private Long version;
}
