package me.chris.bear.data.entity;

import com.querydsl.core.annotations.QuerySupertype;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.Instant;

/**
 * @Author Christopher
 * @Date 2023/11/15
 **/
@MappedSuperclass
@QuerySupertype
@Setter
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractAuditableEntity<PK extends Serializable> extends AbstractVersionedEntity<PK> implements Serializable {

    @CreatedBy
    @Column(name = "create_by")
    private Long createBy;

    @LastModifiedBy
    @Column(name = "update_by")
    private Long updateBy;

    @CreatedDate
    @Column(name = "create_time", updatable = false, nullable = false)
    private Instant createTime;

    @LastModifiedDate
    @Column(name = "update_time", nullable = false)
    private Instant updateTime;

}

