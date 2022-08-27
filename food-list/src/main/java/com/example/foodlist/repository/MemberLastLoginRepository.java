package com.example.foodlist.repository;

import com.example.foodlist.domain.MemberLastLogin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface MemberLastLoginRepository extends JpaRepository<MemberLastLogin,Long> {
    List<MemberLastLogin> findAllByLastLoginTime(LocalDateTime time);
}
