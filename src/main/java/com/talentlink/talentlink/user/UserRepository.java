package com.talentlink.talentlink.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // 사용자명으로 사용자 조회 (로그인 등 인증용)
    Optional<User> findByUsername(String username);

    // 사용자명 중복 확인 (회원가입 시 사용)
    boolean existsByUsername(String username);
}
