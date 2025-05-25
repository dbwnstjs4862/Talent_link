package com.talentlink.talentlink.chat;

import com.talentlink.talentlink.chat.dto.ChatMessageDto;
import com.talentlink.talentlink.chat.dto.ChatRoomResponse;
import com.talentlink.talentlink.user.User;
import com.talentlink.talentlink.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Map;

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
     * 서버 → /sub/chat/room/{roomId}, /sub/chat/refresh/{userId}
     */
    @MessageMapping("/chat/message")
    public void handleMessage(@Payload ChatMessageDto messageDto) {
        ChatRoom room = chatRoomService.findById(messageDto.getRoomId());
        User sender = userService.findById(messageDto.getSenderId());
        User receiver = userService.findById(messageDto.getReceiverId());

        // 🧠 메시지 저장 (단, sender는 읽음처리 안 되도록 서비스 내부에서 분기처리 필요)
        ChatMessage saved = chatMessageService.sendMessage(room.getId(), sender, receiver, messageDto.getContent());

        // 1) 채팅방 내부 사용자들에게 메시지 전송
        messagingTemplate.convertAndSend("/sub/chat/room/" + room.getId(), ChatMessageDto.from(saved));

        // 2) 채팅방 요약 정보 (마지막 메시지, 안읽은 수) 실시간으로 전송
        ChatRoomResponse receiverSummary = chatRoomService.getChatRoomSummary(room, receiver);
        messagingTemplate.convertAndSend("/sub/chat/refresh/" + receiver.getId(), receiverSummary);

        ChatRoomResponse senderSummary = chatRoomService.getChatRoomSummary(room, sender);
        messagingTemplate.convertAndSend("/sub/chat/refresh/" + sender.getId(), senderSummary);

        System.out.println("👉 sender.getId(): " + sender.getId());
        System.out.println("👉 receiver.getId(): " + receiver.getId());
        System.out.println("👉 SEND TO /sub/chat/refresh/" + sender.getId());
        System.out.println("👉 SEND TO /sub/chat/refresh/" + receiver.getId());
        System.out.println("[WS][refresh] receiverSummary: " + receiverSummary.getLastMessage());
        System.out.println("[WS][refresh] senderSummary: " + senderSummary.getLastMessage());
    }

    /**
     * 2️⃣ 읽음 처리 이벤트
     * 클라이언트 → /pub/chat/read
     * 서버 → /sub/chat/read/{보낸사람ID}, /sub/chat/refresh/{보낸사람ID}
     */
    @MessageMapping("/chat/read")
    public void handleRead(@Payload ChatMessageDto messageDto) {
        System.out.println("🧠 [WS 수신] 읽음 처리 요청 들어옴! senderId=" + messageDto.getSenderId() + ", receiverId=" + messageDto.getReceiverId());

        ChatRoom room = chatRoomService.findById(messageDto.getRoomId());
        User receiver = userService.findById(messageDto.getReceiverId());

        // 1️⃣ DB에 읽음 처리 + 모든 메시지 ID 반환
        List<Long> readMessageIds = chatMessageService.markMessagesAsReadAndReturnIds(room, receiver);

        // 2️⃣ 모든 읽은 메시지 ID (리시버 기준) → 샌더에게 전송
        messagingTemplate.convertAndSend("/sub/chat/read/" + messageDto.getSenderId(), readMessageIds);

        // 3️⃣ 요약 정보도 갱신
        User sender = userService.findById(messageDto.getSenderId());
        ChatRoomResponse updatedSummary = chatRoomService.getChatRoomSummary(room, sender);
        messagingTemplate.convertAndSend("/sub/chat/refresh/" + sender.getId(), updatedSummary);

        // ✅ 4️⃣ '샌더가 보낸 메시지 중에서 읽힌 것'만 추려서 샌더에게 알림
        List<Long> senderMessageIds = chatMessageService.findReadMessagesSentByUserInRoom(room.getId(), sender.getId());
        messagingTemplate.convertAndSend("/sub/chat/read/" + sender.getId(), senderMessageIds);
    }

}
