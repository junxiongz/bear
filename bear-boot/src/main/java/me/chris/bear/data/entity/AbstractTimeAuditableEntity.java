package me.chris.bear.data.entity;

import com.querydsl.core.annotations.QuerySupertype;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.Instant;

/**
 * @author Christopher
 * @date 2023/11/15
 **/
@MappedSuperclass
@QuerySupertype
@Setter
@Getter
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractTimeAuditableEntity <PK extends Serializable>
        extends AbstractVersionedEntity<PK>{

    @CreatedDate
    @Column(name = "create_time", updatable = false, nullable = false)
    private Instant createTime;

    @LastModifiedDate
    @Column(name = "update_time", nullable = false)
    private Instant updateTime;

}
