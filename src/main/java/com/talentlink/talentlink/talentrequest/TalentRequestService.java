package com.talentlink.talentlink.talentrequest;

import com.talentlink.talentlink.talentrequest.dto.TalentRequestRequest;
import com.talentlink.talentlink.talentrequest.dto.TalentRequestResponse;
import com.talentlink.talentlink.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TalentRequestService {

    private final TalentRequestRepository requestRepository;

    @Transactional
    public Long register(TalentRequestRequest request, User user) {
        TalentRequest entity = new TalentRequest();
        entity.setTitle(request.getTitle());
        entity.setDescription(request.getDescription());
        entity.setBudget(request.getBudget());
        entity.setDeadline(request.getDeadline());
        entity.setRequester(user);

        return requestRepository.save(entity).getId();
    }

    public List<TalentRequestResponse> findAll() {
        return requestRepository.findAll().stream()
                .map(TalentRequestResponse::new)
                .collect(Collectors.toList());
    }

    public List<TalentRequestResponse> findMyRequests(User user) {
        return requestRepository.findByRequester(user).stream()
                .map(TalentRequestResponse::new)
                .collect(Collectors.toList());
    }

    public TalentRequestResponse findById(Long id) {
        return new TalentRequestResponse(
                requestRepository.findById(id)
                        .orElseThrow(() -> new IllegalArgumentException("요청글을 찾을 수 없습니다. ID=" + id))
        );
    }

    @Transactional(readOnly = true)
    public List<TalentRequestResponse> findLatestRequests(int limit) {
        return requestRepository.findTopByOrderByCreatedAtDesc(PageRequest.of(0, limit)).stream()
                .map(TalentRequestResponse::new)
                .collect(Collectors.toList());
    }

}

