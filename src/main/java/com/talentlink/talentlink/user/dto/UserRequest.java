package com.talentlink.talentlink.user.dto;

public class UserRequest {

    private String username;
    private String password;
    private String nickname;

    // 기본 생성자
    public UserRequest() {}

    // 생성자, getter/setter
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getNickname() { return nickname; }
    public void setNickname(String nickname) { this.nickname = nickname; }
}
