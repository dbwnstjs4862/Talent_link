package com.talentlink.talentlink.talentrequest;

import com.talentlink.talentlink.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TalentRequestService {

    private final TalentRequestRepository talentRequestRepository;

    public Long register(String title, String description, int budget, LocalDateTime deadline, User user) {
        TalentRequest request = new TalentRequest();
        request.setTitle(title);
        request.setDescription(description);
        request.setBudget(budget);
        request.setDeadline(deadline); // ✅ 추가됨
        request.setUser(user);
        return talentRequestRepository.save(request).getId();
    }


    @Transactional(readOnly = true)
    public List<TalentRequest> findAll() {
        return talentRequestRepository.findAllByOrderByCreatedAtDesc();
    }

    @Transactional(readOnly = true)
    public List<TalentRequest> findByUser(User user) {
        return talentRequestRepository.findByUserOrderByCreatedAtDesc(user);
    }

    @Transactional(readOnly = true)
    public TalentRequest findById(Long id) {
        return talentRequestRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 요청이 없습니다. ID=" + id));
    }

    // ✅ 최신 요청 n개 가져오기
    @Transactional(readOnly = true)
    public List<TalentRequest> findLatestRequests(int limit) {
        return talentRequestRepository.findAllByOrderByCreatedAtDesc(PageRequest.of(0, limit));
    }
}
