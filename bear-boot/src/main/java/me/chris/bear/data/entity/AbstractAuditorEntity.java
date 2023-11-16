package me.chris.bear.data.entity;

import com.querydsl.core.annotations.QuerySupertype;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public abstract class AbstractAuditorEntity<U, PK extends Serializable>
        extends AbstractTimeAuditableEntity<PK>  {

    @CreatedBy
    @JoinColumn(name = "create_by", updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private U createBy;

    @LastModifiedBy
    @JoinColumn(name = "update_by")
    @ManyToOne(fetch = FetchType.LAZY)
    private U updateBy;

}
