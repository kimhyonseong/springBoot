package com.example.foodpreference.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
@Setter
@Getter
@EntityListeners(value = {AuditingEntityListener.class})
abstract public class BaseEntity {
  @CreatedDate
  private LocalDateTime regDate;

  @LastModifiedDate
  private LocalDateTime updDate;
}
