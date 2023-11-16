package me.chris.bear.data.entity;

import com.querydsl.core.annotations.QuerySupertype;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import java.io.Serializable;

/**
 * @author Christopher
 * @date 2023/11/15
 **/
@MappedSuperclass
@QuerySupertype
@Setter
@Getter
public abstract class AbstractAuditableEntity<PK extends Serializable>
        extends AbstractTimeAuditableEntity<PK> {

    @CreatedBy
    private String createBy;

    @LastModifiedBy
    private String updateBy;

}

