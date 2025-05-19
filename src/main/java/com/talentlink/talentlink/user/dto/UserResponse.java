package com.talentlink.talentlink.user.dto;

import com.talentlink.talentlink.user.User;
import com.talentlink.talentlink.user.UserRole;

public class UserResponse {
    private Long id;
    private String username;
    private String nickname;
    private UserRole role;

    public UserResponse(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.nickname = user.getNickname();
        this.role = user.getRole();
    }

    public Long getId() { return id; }
    public String getUsername() { return username; }
    public String getNickname() { return nickname; }
    public UserRole getRole() { return role; }
}
