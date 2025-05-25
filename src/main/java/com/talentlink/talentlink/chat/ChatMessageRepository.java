package com.talentlink.talentlink.chat;

import com.talentlink.talentlink.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {


    List<ChatMessage> findByChatRoomOrderBySentAtAsc(ChatRoom chatRoom);
    // 마지막 메시지 1건 (가장 최신)

    ChatMessage findTopByChatRoomOrderBySentAtDesc(ChatRoom chatRoom);
    // 안 읽은 메시지 수 (내가 수신자이고 읽지 않음)

    int countByChatRoomAndReceiverAndIsReadFalse(ChatRoom chatRoom, User receiver);

    List<ChatMessage> findByChatRoomAndReceiverAndIsReadFalse(ChatRoom chatRoom, User receiver);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE ChatMessage m SET m.isRead = true WHERE m.chatRoom = :room AND m.receiver = :receiver AND m.isRead = false")
    int markAllAsRead(@Param("room") ChatRoom room, @Param("receiver") User receiver);

    @Query("SELECT m.id FROM ChatMessage m WHERE m.chatRoom.id = :roomId AND m.sender.id = :senderId AND m.isRead = true")
    List<Long> findReadMessageIdsByRoomAndSender(@Param("roomId") Long roomId, @Param("senderId") Long senderId);

}
