package com.talentlink.talentlink.chat;

import com.talentlink.talentlink.chat.dto.ChatRoomResponse;
import com.talentlink.talentlink.user.User;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
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

    // ChatRoomService.java
    public ChatRoom findById(Long id) {
        return chatRoomRepository.findWithUsersById(id) // ✅ 여기!!
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

                    // Lazy 방지용 partner nickname 추출
                    User partner = room.getPartner(user);
                    String partnerNickname = partner.getNickname();

                    return ChatRoomResponse.from(room, partnerNickname, lastMessage, unreadCount);
                })
                .collect(Collectors.toList());
    }

    public ChatRoomResponse getChatRoomSummary(ChatRoom room, User me) {
        User partner = room.getPartner(me);
        System.out.println("[디버깅] partner 클래스: " + partner.getClass()); // <- 실제 클래스 Proxy 확인
        System.out.println("[디버깅] isInitialized: " + Hibernate.isInitialized(partner));

        // ✅ 여기서 프록시 초기화
        String partnerNickname = partner.getNickname();

        ChatMessage lastMessage = chatMessageRepository.findTopByChatRoomOrderBySentAtDesc(room);
        int unreadCount = chatMessageRepository.countByChatRoomAndReceiverAndIsReadFalse(room, me);

        return ChatRoomResponse.from(room, partnerNickname, lastMessage, unreadCount);
    }

}
