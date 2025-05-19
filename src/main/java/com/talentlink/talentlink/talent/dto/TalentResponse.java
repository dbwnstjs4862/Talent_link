package com.talentlink.talentlink.talent.dto;

import com.talentlink.talentlink.talent.Talent;

import java.time.LocalDateTime;

public class TalentResponse {

    private Long id;
    private String title;
    private String description;
    private int price;
    private String nickname;       // 등록한 유저 닉네임
    private LocalDateTime createdAt; // ✅ 작성일 추가

    public TalentResponse(Talent talent) {
        this.id = talent.getId();
        this.title = talent.getTitle();
        this.description = talent.getDescription();
        this.price = talent.getPrice();
        this.nickname = talent.getUser().getNickname();
        this.createdAt = talent.getCreatedAt(); // ✅ 작성일 세팅
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public int getPrice() { return price; }
    public String getNickname() { return nickname; }
    public LocalDateTime getCreatedAt() { return createdAt; } // ✅ Getter 추가
}
