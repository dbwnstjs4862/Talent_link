package com.talentlink.talentlink.user.dto;

public class LoginResponse {
    private Long userId;
    private String nickname;

    public LoginResponse(Long userId, String nickname) {
        this.userId = userId;
        this.nickname = nickname;
    }

    public Long getUserId() { return userId; }
    public String getNickname() { return nickname; }
}