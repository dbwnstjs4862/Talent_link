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
    public ResponseEntity<String> registerTalent(@RequestBody TalentRequest request, @AuthenticationPrincipal CustomUserDetails userDetails) {
        Long id = talentService.registerTalent(request, userDetails.getUser());
        return ResponseEntity.ok("재능 등록 성공! ID: " + id);
    }

    @GetMapping("/my")
    public ResponseEntity<List<TalentResponse>> getMyTalents(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(talentService.findMyTalents(userDetails.getUser()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TalentResponse> getTalentDetail(@PathVariable Long id) {
        return ResponseEntity.ok(talentService.findById(id));
    }
}

