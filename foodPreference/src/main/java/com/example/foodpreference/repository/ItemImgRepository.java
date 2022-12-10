package com.example.foodpreference.repository;

import com.example.foodpreference.domain.Item;
import com.example.foodpreference.domain.ItemImg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.Nullable;

import java.util.Optional;

public interface ItemImgRepository extends JpaRepository<ItemImg,Long> {
  Optional<ItemImg> findByIdx(Long idx);
}
