package com.talentlink.talentlink.chat;

import com.talentlink.talentlink.chat.dto.ChatMessageDto;
import com.talentlink.talentlink.user.User;
import com.talentlink.talentlink.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatWebSocketController {

    private final SimpMessageSendingOperations messagingTemplate;
    private final ChatRoomService chatRoomService;
    private final ChatMessageService chatMessageService;
    private final UserService userService;

    /**
     * 1️⃣ 채팅 메시지 전송 처리
     * 클라이언트 → /pub/chat/message
     * 서버 → /sub/chat/room/{roomId}
     */
    @MessageMapping("/chat/message")
    public void handleMessage(@Payload ChatMessageDto messageDto) {
        ChatRoom room = chatRoomService.findById(messageDto.getRoomId());
        User sender = userService.findById(messageDto.getSenderId());
        User receiver = userService.findById(messageDto.getReceiverId());

        // 메시지 저장
        ChatMessage saved = chatMessageService.sendMessage(room.getId(), sender, receiver, messageDto.getContent());

        // 메시지 전송
        messagingTemplate.convertAndSend("/sub/chat/room/" + room.getId(), ChatMessageDto.from(saved));
    }

    /**
     * 2️⃣ 읽음 처리 이벤트
     * 클라이언트 → /pub/chat/read
     * 서버 → /sub/chat/read/{보낸사람ID}
     */
    @MessageMapping("/chat/read")
    public void handleRead(@Payload ChatMessageDto messageDto) {
        chatMessageService.markMessagesAsReadByWebSocket(
                messageDto.getRoomId(), messageDto.getReceiverId()
        );

        // 보낸 사람에게 '읽음' 알림 보내기
        messagingTemplate.convertAndSend("/sub/chat/read/" + messageDto.getSenderId(), messageDto.getRoomId());
    }
}
