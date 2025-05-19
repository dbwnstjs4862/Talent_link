package com.talentlink.talentlink.user.dto;

import com.talentlink.talentlink.user.UserRole;

public class UserRequest {

    private String username;
    private String password;
    private String nickname;
    private UserRole role; // or default USER

    // 기본 생성자 (JSON 바인딩용)
    public UserRequest() {}

    // 생성자, getter/setter
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getNickname() { return nickname; }
    public void setNickname(String nickname) { this.nickname = nickname; }

    public UserRole getRole() { return role; }
    public void setRole(UserRole role) { this.role = role; }

    // Entity로 변환
    public com.talentlink.talentlink.user.User toEntity() {
        return new com.talentlink.talentlink.user.User(username, password, nickname, role);
    }
}
