package com.talentlink.talentlink.chat.dto;

import com.talentlink.talentlink.chat.ChatMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor // WebSocket 역직렬화를 위해 필수
@AllArgsConstructor // 생성 테스트 용이
public class ChatMessageDto {

    private Long id;               // 메시지 고유 ID
    private Long roomId;           // 채팅방 ID
    private Long senderId;         // 보낸 사람 ID
    private Long receiverId;       // 수신자 ID
    private String senderNickname; // 보낸 사람 닉네임
    private String content;        // 메시지 내용
    private String type;           // 메시지 타입 (예: TALK, READ)
    private LocalDateTime sentAt;  // 보낸 시각
    private boolean read;          // 읽음 여부

    public static ChatMessageDto from(ChatMessage message) {
        ChatMessageDto dto = new ChatMessageDto();

        dto.id = message.getId();
        dto.roomId = message.getChatRoom().getId();

        if (message.getSender() != null) {
            dto.senderId = message.getSender().getId();
            dto.senderNickname = message.getSender().getNickname();
        }

        if (message.getReceiver() != null) {
            dto.receiverId = message.getReceiver().getId();
        }

        dto.content = message.getContent();
        dto.type = "TALK";
        dto.sentAt = message.getSentAt();
        dto.read = message.isRead();

        return dto;
    }
}
