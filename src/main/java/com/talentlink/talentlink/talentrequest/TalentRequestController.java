package com.talentlink.talentlink.talentrequest;

import com.talentlink.talentlink.security.CustomUserDetails;
import com.talentlink.talentlink.talentrequest.dto.TalentRequestRequest;
import com.talentlink.talentlink.talentrequest.dto.TalentRequestResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/talentrequests")
@RequiredArgsConstructor
public class TalentRequestController {

    private final TalentRequestService requestService;

    // ✅ 등록
    @PostMapping
    public ResponseEntity<Long> registerRequest(
            @RequestBody TalentRequestRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        Long id = requestService.register(request, userDetails.getUser());
        return ResponseEntity.ok(id);
    }

    // ✅ 전체 목록
    @GetMapping
    public ResponseEntity<List<TalentRequestResponse>> getAllRequests() {
        return ResponseEntity.ok(requestService.findAll());
    }

    // ✅ 상세 보기
    @GetMapping("/{id}")
    public ResponseEntity<TalentRequestResponse> getRequestDetail(@PathVariable Long id) {
        return ResponseEntity.ok(requestService.findById(id));
    }

    // ✅ 내 요청글 목록
    @GetMapping("/my")
    public ResponseEntity<List<TalentRequestResponse>> getMyRequests(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        return ResponseEntity.ok(requestService.findMyRequests(userDetails.getUser()));
    }
}
