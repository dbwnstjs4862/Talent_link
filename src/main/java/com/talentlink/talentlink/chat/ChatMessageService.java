package com.talentlink.talentlink.chat;

import com.talentlink.talentlink.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;

    public ChatMessage save(ChatRoom room, User sender, User receiver, String content) {
        ChatMessage msg = new ChatMessage();
        msg.setChatRoom(room);
        msg.setSender(sender);
        msg.setReceiver(receiver);
        msg.setContent(content);
        msg.setRead(false);
        msg.setSentAt(LocalDateTime.now());
        return chatMessageRepository.save(msg);
    }

    public List<ChatMessage> findMessages(ChatRoom room) {
        return chatMessageRepository.findByChatRoomOrderBySentAtAsc(room);
    }

    public void markMessagesAsRead(ChatRoom room, User receiver) {
        List<ChatMessage> unreadMessages = chatMessageRepository.findByChatRoomOrderBySentAtAsc(room).stream()
                .filter(msg -> msg.getReceiver().getId().equals(receiver.getId()) && !msg.isRead())
                .toList();

        unreadMessages.forEach(msg -> msg.setRead(true));
    }
}
