package com.talentlink.talentlink.chat.dto;

import com.talentlink.talentlink.chat.ChatMessage;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ChatMessageDto {

    private Long roomId;           // 채팅방 ID
    private Long senderId;         // 보낸 사람 ID
    private Long receiverId;       // 수신자 ID (❗추가)
    private String senderNickname; // 보낸 사람 닉네임
    private String content;        // 메시지 내용
    private String type;           // 메시지 타입 (예: TALK, READ)
    private LocalDateTime sentAt;  // 보낸 시각
    private boolean read;        // 읽음 여부 (❗추가)

    public static ChatMessageDto from(ChatMessage message) {
        ChatMessageDto dto = new ChatMessageDto();
        dto.setRoomId(message.getChatRoom().getId());
        dto.setSenderId(message.getSender().getId());
        dto.setSenderNickname(message.getSender().getNickname());
        dto.setContent(message.getContent());
        dto.setType("TALK");
        dto.setSentAt(message.getSentAt());
        dto.setRead(message.isRead());

        if (message.getReceiver() != null) {
            dto.setReceiverId(message.getReceiver().getId());
        }

        return dto;
    }
}
