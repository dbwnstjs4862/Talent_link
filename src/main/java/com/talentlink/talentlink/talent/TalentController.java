package com.talentlink.talentlink.talent;

import com.talentlink.talentlink.security.CustomUserDetails;
import com.talentlink.talentlink.talent.dto.TalentRequest;
import com.talentlink.talentlink.talent.dto.TalentResponse;
import com.talentlink.talentlink.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/talents")
@RequiredArgsConstructor
public class TalentController {

    private final TalentService talentService;

    @PostMapping
    public ResponseEntity<String> registerTalent(
            @RequestBody TalentRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        User loginUser = userDetails.getUser();
        Long talentId = talentService.registerTalent(request, loginUser);
        return ResponseEntity.ok("재능 등록 성공! ID: " + talentId);
    }

    @GetMapping("/my")
    public ResponseEntity<List<TalentResponse>> getMyTalents(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        User loginUser = userDetails.getUser();
        List<TalentResponse> myTalents = talentService.findMyTalents(loginUser);
        return ResponseEntity.ok(myTalents);
    }

    // ✅ API용 재능 상세 조회 추가됨
    @GetMapping("/{id}")
    public ResponseEntity<TalentResponse> getTalentDetail(
            @PathVariable Long id
    ) {
        TalentResponse talent = talentService.findById(id);
        return ResponseEntity.ok(talent);
    }
}

