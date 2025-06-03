package com.talentlink.talentlink.chat;

import com.talentlink.talentlink.user.User;
import com.talentlink.talentlink.global.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
@Entity
@Getter
@Setter
public class ChatRoom extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_a_id")
    private User userA;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_b_id")
    private User userB;

    public User getPartner(User me) {
        return me.getId().equals(userA.getId()) ? userB : userA;
    }
}







