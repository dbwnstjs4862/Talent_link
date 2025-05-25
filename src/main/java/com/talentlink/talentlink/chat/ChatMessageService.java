package com.talentlink.talentlink.chat;

import com.talentlink.talentlink.user.User;
import com.talentlink.talentlink.user.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;

    /**
     * 3️⃣ 읽지 않은 메시지 읽고 ID 리스트 반환 (읽음 알림용)
     */
    @PersistenceContext
    private EntityManager em;

    /**
     * 1️⃣ 메시지 저장
     */
    @Transactional
    public ChatMessage sendMessage(Long roomId, User sender, User receiver, String content) {
        ChatRoom room = chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("채팅방이 없습니다"));

        ChatMessage message = new ChatMessage(room, sender, receiver, content);

        System.out.println("🧾 메시지 저장: sender=" + sender.getId() + ", receiver=" + (receiver != null ? receiver.getId() : "null"));

        return chatMessageRepository.save(message);
    }



    /**
     * 2️⃣ 읽지 않은 메시지 읽음 처리 (WebSocket 전용)
     */
    @Transactional
    public void markMessagesAsReadByWebSocket(Long roomId, Long receiverId) {
        ChatRoom room = chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("채팅방이 없습니다"));
        User receiver = userRepository.findById(receiverId)
                .orElseThrow(() -> new IllegalArgumentException("사용자가 없습니다"));

        chatMessageRepository.findByChatRoomAndReceiverAndIsReadFalse(room, receiver)
                .forEach(ChatMessage::markAsRead);
    }

    @Transactional
    public List<Long> markMessagesAsReadAndReturnIds(ChatRoom room, User receiver) {
        System.out.println("📥 읽음 처리 대상 receiver=" + receiver.getId());
        List<ChatMessage> unreadMessages =
                chatMessageRepository.findByChatRoomAndReceiverAndIsReadFalse(room, receiver);

        System.out.println("📬 안 읽은 메시지 수=" + unreadMessages.size());
        for (ChatMessage msg : unreadMessages) {
            System.out.println("  - msgId=" + msg.getId() + ", isRead=" + msg.isRead() + ", receiver=" + msg.getReceiver().getId());
        }

        if (!unreadMessages.isEmpty()) {
            chatMessageRepository.markAllAsRead(room, receiver);
            em.flush();
        }

        return unreadMessages.stream()
                .map(ChatMessage::getId)
                .collect(Collectors.toList());
    }




    /**
     * 4️⃣ 일반 읽음 처리 (컨트롤러에서 직접 호출용)
     */
    @Transactional
    public void markMessagesAsRead(ChatRoom room, User receiver) {
        chatMessageRepository.findByChatRoomAndReceiverAndIsReadFalse(room, receiver)
                .forEach(ChatMessage::markAsRead);
    }

    /**
     * 5️⃣ 채팅방의 전체 메시지 조회
     */
    public List<ChatMessage> findMessagesByRoom(ChatRoom room) {
        return chatMessageRepository.findByChatRoomOrderBySentAtAsc(room);
    }

    public List<Long> findReadMessagesSentByUserInRoom(Long roomId, Long senderId) {
        return chatMessageRepository.findReadMessageIdsByRoomAndSender(roomId, senderId);
    }

}
