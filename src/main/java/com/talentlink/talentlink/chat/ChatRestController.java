package com.talentlink.talentlink.chat;

import com.talentlink.talentlink.chat.dto.ChatMessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}

