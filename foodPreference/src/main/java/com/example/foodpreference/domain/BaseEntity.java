package com.example.foodpreference.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
@Setter
@Getter
@EntityListeners(value = {AuditingEntityListener.class})
abstract public class BaseEntity {
  @CreatedDate
  @Column(name = "reg_date", updatable = false, columnDefinition = "datetime(6) default now(6) comment '생성시간'")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMddHHmmss")
  private LocalDateTime regDate;

  @LastModifiedDate
  @Column(name = "upd_date", updatable = true, columnDefinition = "datetime(6) comment '수정시간'")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMddHHmmss")
  private LocalDateTime updDate;
}
