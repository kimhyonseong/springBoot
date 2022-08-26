package com.example.foodlist.repository;

import com.example.foodlist.domain.MemberLogin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberLoginRepository extends JpaRepository<MemberLogin,Long> {
}
