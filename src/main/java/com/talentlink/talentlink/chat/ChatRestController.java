package com.talentlink.talentlink.chat;

import com.talentlink.talentlink.chat.dto.ChatMessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat")
public class ChatRestController {

    private final ChatRoomService chatRoomService;
    private final ChatMessageService chatMessageService;

    @GetMapping("/messages/{roomId}")
    public List<ChatMessageDto> getMessages(@PathVariable Long roomId) {
        ChatRoom room = chatRoomService.findById(roomId);
        return chatMessageService.findMessagesByRoom(room).stream()
                .map(ChatMessageDto::from)
                .toList();
    }

    // ✅ 내가 보낸 메시지 중 읽힌 것만 반환
    @GetMapping("/read-messages/{roomId}")
    public List<Long> getReadMessages(
            @PathVariable Long roomId,
            @RequestParam Long userId
    ) {
        return chatMessageService.findReadMessagesSentByUserInRoom(roomId, userId);
    }
}


