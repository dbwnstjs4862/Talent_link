package com.talentlink.talentlink.chat;

import com.talentlink.talentlink.chat.dto.ChatRoomResponse;
import com.talentlink.talentlink.user.User;
import com.talentlink.talentlink.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatRoomRestController {

    private final ChatRoomService chatRoomService;
    private final ChatMessageService chatMessageService;
    private final UserService userService;

    @GetMapping("/room-summary/{roomId}")
    public ChatRoomResponse getRoomSummary(
            @PathVariable Long roomId,
            @RequestParam Long userId
    ) {
        ChatRoom room = chatRoomService.findById(roomId);
        User user = userService.findById(userId);
        return chatRoomService.getChatRoomSummary(room, user); // ✅ 이걸 쓰면 됨 (서비스에 위임)
    }
}

