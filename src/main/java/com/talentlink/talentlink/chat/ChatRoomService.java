package com.talentlink.talentlink.chat;

import com.talentlink.talentlink.chat.dto.ChatRoomResponse;
import com.talentlink.talentlink.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;

    public ChatRoom createRoom(User userA, User userB) {
        ChatRoom room = new ChatRoom();
        room.setUserA(userA);
        room.setUserB(userB);
        return chatRoomRepository.save(room);
    }

    public ChatRoom findById(Long id) {
        return chatRoomRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("채팅방이 존재하지 않습니다. ID=" + id));
    }

    public ChatRoom findBetweenUsers(User userA, User userB) {
        return chatRoomRepository.findByUserAAndUserB(userA, userB)
                .orElse(null);
    }

    public List<ChatRoom> findRoomsByUser(User user) {
        return chatRoomRepository.findByUserAOrUserB(user, user);
    }

    @Transactional(readOnly = true)
    public List<ChatRoom> getChatRoomsWithDetails(User user) {
        List<ChatRoom> rooms = findRoomsByUser(user);

        for (ChatRoom room : rooms) {
            // Lazy 로딩 방지용 partner 가져오기
            room.getPartner(user).getNickname();
            chatMessageRepository.findTopByChatRoomOrderBySentAtDesc(room);
            chatMessageRepository.countByChatRoomAndReceiverAndIsReadFalse(room, user);
        }

        return rooms;
    }

    public List<ChatRoomResponse> getChatRoomResponsesWithDetails(User user) {
        List<ChatRoom> rooms = findRoomsByUser(user);

        return rooms.stream().map(room -> {
            User partner = room.getPartner(user);
            ChatMessage lastMessage = chatMessageRepository.findTopByChatRoomOrderBySentAtDesc(room);
            int unreadCount = chatMessageRepository.countByChatRoomAndReceiverAndIsReadFalse(room, user);

            return ChatRoomResponse.from(room, partner.getNickname(), lastMessage, unreadCount);
        }).toList();
    }

}