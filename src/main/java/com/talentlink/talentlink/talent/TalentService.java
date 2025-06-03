package com.talentlink.talentlink.talent;

import com.talentlink.talentlink.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TalentService {

    private final TalentRepository talentRepository;

    public Long register(String title, String description, int price, User user) {
        Talent talent = new Talent();
        talent.setTitle(title);
        talent.setDescription(description);
        talent.setPrice(price);
        talent.setUser(user);
        return talentRepository.save(talent).getId();
    }

    public List<Talent> findAll() {
        return talentRepository.findAllWithUser();
    }

    @Transactional(readOnly = true)
    public List<Talent> findByUser(User user) {
        return talentRepository.findByUserOrderByCreatedAtDesc(user);
    }

    @Transactional(readOnly = true)
    public Talent findById(Long id) {
        return talentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 재능이 없습니다. ID=" + id));
    }

    @Transactional(readOnly = true)
    public List<Talent> findLatestTalents(int limit) {
        return talentRepository.findAllByOrderByCreatedAtDesc(PageRequest.of(0, limit));
    }

}
