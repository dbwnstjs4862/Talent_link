package com.talentlink.talentlink.talentrequest;


import com.talentlink.talentlink.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class TalentRequest {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    private int budget; // 희망 가격

    private LocalDateTime deadline; // 요청 마감일

    private LocalDateTime createdAt = LocalDateTime.now(); // 요청글 등록일

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User requester; // 요청한 사람 (구매자)
}

