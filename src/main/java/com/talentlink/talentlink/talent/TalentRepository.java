package com.talentlink.talentlink.talent;

import com.talentlink.talentlink.user.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TalentRepository extends JpaRepository<Talent, Long> {
    List<Talent> findAllByOrderByCreatedAtDesc();
    List<Talent> findByUserOrderByCreatedAtDesc(User user);
    List<Talent> findAllByOrderByCreatedAtDesc(Pageable pageable);
    @Query("SELECT t FROM Talent t JOIN FETCH t.user ORDER BY t.createdAt DESC")
    List<Talent> findAllWithUser();

}
