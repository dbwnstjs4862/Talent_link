package com.talentlink.talentlink.chat;

import com.talentlink.talentlink.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 채팅 참여자 A
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_a_id")
    private User userA;

    // 채팅 참여자 B
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_b_id")
    private User userB;

    private LocalDateTime createdAt;

    public ChatRoom(User userA, User userB) {
        this.userA = userA;
        this.userB = userB;
        this.createdAt = LocalDateTime.now();
    }

    public User getPartner(User me) {
        if (userA.getId().equals(me.getId())) {
            return userB;
        }
        return userA;
    }

}
