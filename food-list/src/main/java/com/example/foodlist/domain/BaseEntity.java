package com.example.foodlist.domain;

import lombok.Data;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@MappedSuperclass  // 테이블로 생성되지 않게 함
@EntityListeners(value = {AuditingEntityListener.class})
abstract class BaseEntity {
    @CreatedDate
    @Column(name = "regDate", updatable = false, columnDefinition = "datetime(6) default now(6) comment '생성시간'")
    private LocalDateTime regDate;

    @LastModifiedDate
    @Column(name = "updDate", updatable = false, columnDefinition = "datetime(6) default now(6) comment '수정시간'")
    private LocalDateTime updDate;
}
