package com.talentlink.talentlink.chat;

import com.talentlink.talentlink.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    List<ChatRoom> findByUserAOrUserB(User userA, User userB);
    Optional<ChatRoom> findByUserAAndUserB(User userA, User userB);
}