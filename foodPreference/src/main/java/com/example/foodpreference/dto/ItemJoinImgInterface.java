package com.example.foodpreference.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public interface ItemJoinImgInterface {
  public Long getIdx();

  public Long getItem_idx();

  public String getCode();

  public String getName();

  public String getDescription();

  public int getState();

  public int getPrice();

  public int getQuantity();

  public Long getMemberIdx();

  public Long getImg_idx();

  public String getImgPath();

  public String getFileName();

  public String getOriginFileName();

  public Integer getSize();

  public String getExtension();
}
