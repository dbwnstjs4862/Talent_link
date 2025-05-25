package com.talentlink.talentlink.chat;

import com.talentlink.talentlink.chat.dto.ChatRoomResponse;
import com.talentlink.talentlink.chat.ChatMessageService;
import com.talentlink.talentlink.security.CustomUserDetails;
import com.talentlink.talentlink.user.User;
import com.talentlink.talentlink.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ChatViewController {

    private final ChatRoomService chatRoomService;
    private final ChatMessageService chatMessageService;
    private final UserService userService;

    @GetMapping("/chat/rooms")
    public String viewChatRoomList(@AuthenticationPrincipal CustomUserDetails userDetails,
                                   Model model) {
        User me = userDetails.getUser();
        List<ChatRoomResponse> responses = chatRoomService.getChatRoomsWithDetails(me);
        model.addAttribute("rooms", responses);
        model.addAttribute("currentUserId", me.getId()); // ✅ 추가해줘야 JS에서 userId 동작함
        return "chat/chat-room-list";
    }

    @GetMapping("/chat/room/{roomId}")
    public String viewChatRoom(@PathVariable Long roomId, Model model,
                               @AuthenticationPrincipal CustomUserDetails userDetails) {
        User me = userDetails.getUser();
        ChatRoom room = chatRoomService.findById(roomId);

        // ✅ 메시지 읽음 처리
        chatMessageService.markMessagesAsRead(room, me);

        // ✅ 상대방 ID 계산 및 전달 (✨ 추가 부분)
        User other = room.getPartner(me);
        model.addAttribute("otherUserId", other.getId());

        // ✅ 프론트에 전달할 기본 정보
        model.addAttribute("roomId", roomId);
        model.addAttribute("currentUserId", me.getId());
        model.addAttribute("nickname", me.getNickname());

        return "chat/chat-room";
    }

    @GetMapping("/chat/start/{targetUserId}")
    public String startChatWithUser(@PathVariable Long targetUserId,
                                    @AuthenticationPrincipal CustomUserDetails userDetails) {
        User me = userDetails.getUser();
        User other = userService.findById(targetUserId);

        // 항상 작은 ID가 userA가 되도록 정렬 (중복 방지)
        User userA = me.getId() < other.getId() ? me : other;
        User userB = me.getId() < other.getId() ? other : me;

        // 두 유저 사이에 기존 채팅방이 있는지 확인
        ChatRoom existingRoom = chatRoomService.findBetweenUsers(userA, userB);

        if (existingRoom != null) {
            return "redirect:/chat/room/" + existingRoom.getId();
        }

        // 없으면 새로 생성
        ChatRoom newRoom = chatRoomService.createRoom(userA, userB);
        return "redirect:/chat/room/" + newRoom.getId();
    }

}
