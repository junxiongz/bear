package me.chris.bear.data.entity;

import com.querydsl.core.annotations.QuerySupertype;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.Nullable;
import java.io.Serializable;

/**
 * @Author Christopher
 * @Since 2023/11/21 1:35 PM
 */
@MappedSuperclass
@QuerySupertype
@Setter
@Getter
public class AbstractEntity<PK extends Serializable> extends AbstractPersistableIdLess<PK> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Nullable
    private PK id;
}
