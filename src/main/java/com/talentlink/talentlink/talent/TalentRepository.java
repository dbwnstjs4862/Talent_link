package com.talentlink.talentlink.talent;

import com.talentlink.talentlink.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TalentRepository extends JpaRepository<Talent, Long> {

    // 특정 유저가 등록한 재능 목록 조회
    List<Talent> findByUser(User user);

    // ✅ 전체 재능 최신순 정렬
    List<Talent> findAllByOrderByCreatedAtDesc();

    // ✅ 특정 유저 재능 최신순 정렬
    List<Talent> findByUserOrderByCreatedAtDesc(User user);
}
