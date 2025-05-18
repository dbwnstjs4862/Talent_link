package com.talentlink.talentlink.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);  // 로그인용으로 사용

    boolean existsByUsername(String username);        // 중복확인용
}

