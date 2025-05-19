package com.talentlink.talentlink.talent;

import com.talentlink.talentlink.talent.dto.TalentRequest;
import com.talentlink.talentlink.talent.dto.TalentResponse;
import com.talentlink.talentlink.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class TalentService {

    private final TalentRepository talentRepository;

    // 재능 등록
    public Long registerTalent(TalentRequest request, User user) {
        Talent talent = new Talent();
        talent.setTitle(request.getTitle());
        talent.setDescription(request.getDescription());
        talent.setPrice(request.getPrice());
        talent.setUser(user);

        return talentRepository.save(talent).getId();
    }

    // 내 재능 목록 조회
    @Transactional(readOnly = true)
    public List<TalentResponse> findMyTalents(User user) {
        return talentRepository.findByUserOrderByCreatedAtDesc(user) // ✅ 최신순 정렬
                .stream()
                .map(TalentResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public TalentResponse findById(Long id) {
        Talent talent = talentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 재능이 없습니다. ID=" + id));
        return new TalentResponse(talent);
    }

    @Transactional(readOnly = true)
    public List<TalentResponse> findAllTalents() {
        return talentRepository.findAllByOrderByCreatedAtDesc() // ✅ 최신순 정렬 적용
                .stream()
                .map(TalentResponse::new)
                .collect(Collectors.toList());
    }

}
