package com.example.foodpreference.repository;

import com.example.foodpreference.domain.ItemImg;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemImgRepository extends JpaRepository<ItemImg,Long> {
  ItemImg findByIdx(Long idx);
}
