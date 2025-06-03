package com.talentlink.talentlink.chat;

import com.talentlink.talentlink.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    List<ChatMessage> findByChatRoomOrderBySentAtAsc(ChatRoom chatRoom);

    ChatMessage findTopByChatRoomOrderBySentAtDesc(ChatRoom chatRoom);

    int countByChatRoomAndReceiverAndIsReadFalse(ChatRoom chatRoom, User receiver);
}
