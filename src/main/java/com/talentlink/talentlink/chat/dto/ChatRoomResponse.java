package com.talentlink.talentlink.chat.dto;

import com.talentlink.talentlink.chat.ChatMessage;
import com.talentlink.talentlink.chat.ChatRoom;
import com.talentlink.talentlink.user.User;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ChatRoomResponse {

    private Long roomId;
    private String partnerNickname;
    private LocalDateTime createdAt;

    private String lastMessage;
    private LocalDateTime lastMessageTime;
    private int unreadCount;

    public static ChatRoomResponse from(ChatRoom room, String partnerNickname, ChatMessage lastMessage, int unreadCount) {
        return ChatRoomResponse.builder()
                .roomId(room.getId())
                .partnerNickname(partnerNickname)
                .createdAt(room.getCreatedAt())
                .lastMessage(lastMessage != null ? lastMessage.getContent() : "")
                .lastMessageTime(lastMessage != null ? lastMessage.getSentAt() : null)
                .unreadCount(unreadCount)
                .build();
    }
}