package com.talentlink.talentlink.chat;

import com.talentlink.talentlink.chat.ChatMessage;
import com.talentlink.talentlink.chat.ChatMessageRepository;
import com.talentlink.talentlink.chat.ChatRoom;
import com.talentlink.talentlink.chat.ChatRoomRepository;
import com.talentlink.talentlink.user.User;
import com.talentlink.talentlink.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;

    /**
     * 1️⃣ 메시지 저장
     */
    @Transactional
    public ChatMessage sendMessage(Long roomId, User sender, User receiver, String content) {
        ChatRoom room = chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("채팅방이 없습니다"));

        ChatMessage message = new ChatMessage(room, sender, receiver, content);
        return chatMessageRepository.save(message);
    }

    /**
     * 2️⃣ 읽지 않은 메시지들을 읽음 처리
     */
    @Transactional
    public void markMessagesAsReadByWebSocket(Long roomId, Long receiverId) {
        ChatRoom room = chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("채팅방이 없습니다"));
        User receiver = userRepository.findById(receiverId)
                .orElseThrow(() -> new IllegalArgumentException("사용자가 없습니다"));

        List<ChatMessage> unreadMessages =
                chatMessageRepository.findByChatRoomAndReceiverAndIsReadFalse(room, receiver);

        for (ChatMessage message : unreadMessages) {
            message.markAsRead();
        }
    }

    @Transactional
    public void markMessagesAsRead(ChatRoom room, User receiver) {
        List<ChatMessage> unreadMessages =
                chatMessageRepository.findByChatRoomAndReceiverAndIsReadFalse(room, receiver);

        for (ChatMessage message : unreadMessages) {
            message.markAsRead();
        }
    }

    public List<ChatMessage> findMessagesByRoom(ChatRoom room) {
        return chatMessageRepository.findByChatRoomOrderBySentAtAsc(room);
    }
}
