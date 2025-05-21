package com.talentlink.talentlink.talentrequest;

import com.talentlink.talentlink.user.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TalentRequestRepository extends JpaRepository<TalentRequest, Long> {
    List<TalentRequest> findByRequester(User user);
    List<TalentRequest> findTopByOrderByCreatedAtDesc(Pageable pageable);
}
