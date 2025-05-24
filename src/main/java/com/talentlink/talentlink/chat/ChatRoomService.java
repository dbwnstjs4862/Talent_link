package com.talentlink.talentlink.chat;

import com.talentlink.talentlink.chat.dto.ChatRoomResponse;
import com.talentlink.talentlink.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;

    public ChatRoom createRoom(User userA, User userB) {
        return chatRoomRepository.save(new ChatRoom(userA, userB));
    }

    public ChatRoom findById(Long id) {
        return chatRoomRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("채팅방이 존재하지 않습니다."));
    }

    public List<ChatRoom> findAllByUser(User user) {
        return chatRoomRepository.findByUserAOrUserB(user, user);
    }

    public ChatRoom findBetweenUsers(User userA, User userB) {
        return chatRoomRepository.findByUserAAndUserB(userA, userB)
                .orElse(null);
    }

    // ✅ 채팅방 리스트 + 마지막 메시지 + 안읽은 수 포함된 DTO 반환
    public List<ChatRoomResponse> getChatRoomsWithDetails(User user) {
        List<ChatRoom> rooms = findAllByUser(user);

        return rooms.stream()
                .map(room -> {
                    ChatMessage lastMessage = chatMessageRepository.findTopByChatRoomOrderBySentAtDesc(room);
                    int unreadCount = chatMessageRepository.countByChatRoomAndReceiverAndIsReadFalse(room, user);
                    return ChatRoomResponse.from(room, user, lastMessage, unreadCount);
                })
                .collect(Collectors.toList());
    }
}
