package com.talentlink.talentlink.talentrequest.dto;

import com.talentlink.talentlink.talentrequest.TalentRequest;
import java.time.LocalDateTime;

public class TalentRequestResponse {

    private Long id;
    private String title;
    private String description;
    private int budget;
    private LocalDateTime deadline;
    private String nickname;
    private Long userId; // ✅ 추가
    private LocalDateTime createdAt;

    public TalentRequestResponse(TalentRequest request) {
        this.id = request.getId();
        this.title = request.getTitle();
        this.description = request.getDescription();
        this.budget = request.getBudget();
        this.deadline = request.getDeadline();
        this.createdAt = request.getCreatedAt();
        this.nickname = request.getRequester().getNickname();
        this.userId = request.getRequester().getId(); // ✅ 추가
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public int getBudget() { return budget; }
    public LocalDateTime getDeadline() { return deadline; }
    public String getNickname() { return nickname; }
    public Long getUserId() { return userId; } // ✅ 추가
    public LocalDateTime getCreatedAt() { return createdAt; }
}