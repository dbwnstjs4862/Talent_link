package com.talentlink.talentlink.user.dto;

public class UserUpdateRequest {

    private String nickname;
    private String email;
    private String password; // 나중에 비밀번호 변경용
    private String profileImageUrl; // 프로필 이미지 URL (또는 MultipartFile)

    // 기본 생성자, getter/setter 생략 가능하면 Lombok 써도 됨
    public UserUpdateRequest() {
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

}
