package com.talentlink.talentlink.chat;

import com.talentlink.talentlink.chat.dto.ChatRoomResponse;
import com.talentlink.talentlink.security.CustomUserDetails;
import com.talentlink.talentlink.user.User;
import com.talentlink.talentlink.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ChatViewController {

    private final ChatRoomService chatRoomService;
    private final ChatMessageService chatMessageService;
    private final UserService userService;

    @GetMapping("/chat/rooms")
    public String viewChatRoomList(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        Long userId = userDetails.getUser().getId();

        // API 서버 주소
        String apiUrl = "http://localhost:8085/api/chat/rooms?userId=" + userId;

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<ChatRoomResponse[]> response = restTemplate.getForEntity(apiUrl, ChatRoomResponse[].class);

        model.addAttribute("rooms", List.of(response.getBody()));
        model.addAttribute("currentUserId", userId);

        System.out.println("✅ rooms 개수: " + response.getBody().length);
        System.out.println("✅ partnerNickname: " + response.getBody()[0].getPartnerNickname());

        return "chat/chat-room-list";
    }



    @GetMapping("/chat/room/{roomId}")
    public String viewChatRoom(@PathVariable Long roomId, Model model,
                               @AuthenticationPrincipal CustomUserDetails userDetails) {
        User me = userDetails.getUser();
        ChatRoom room = chatRoomService.findById(roomId);

        chatMessageService.markMessagesAsRead(room, me);

        User other = room.getPartner(me);
        model.addAttribute("otherUserId", other.getId());
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

        User userA = me.getId() < other.getId() ? me : other;
        User userB = me.getId() < other.getId() ? other : me;

        ChatRoom existingRoom = chatRoomService.findBetweenUsers(userA, userB);
        if (existingRoom != null) {
            return "redirect:/chat/room/" + existingRoom.getId();
        }

        ChatRoom newRoom = chatRoomService.createRoom(userA, userB);
        return "redirect:/chat/room/" + newRoom.getId();
    }
}
