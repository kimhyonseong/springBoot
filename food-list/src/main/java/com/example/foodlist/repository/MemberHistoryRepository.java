package com.example.foodlist.repository;

import com.example.foodlist.domain.MemberHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberHistoryRepository extends JpaRepository<MemberHistory,Long> {
}
