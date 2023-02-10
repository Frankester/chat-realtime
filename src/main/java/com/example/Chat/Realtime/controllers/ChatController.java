package com.example.Chat.Realtime.controllers;

import com.example.Chat.Realtime.exeptions.ChatRoomNotFoundException;
import com.example.Chat.Realtime.exeptions.UserNotMemberException;
import com.example.Chat.Realtime.models.ChatMessage;
import com.example.Chat.Realtime.models.ChatRoom;
import com.example.Chat.Realtime.models.Message;
import com.example.Chat.Realtime.models.Usuario;
import com.example.Chat.Realtime.repositories.RepoChatRoom;
import com.example.Chat.Realtime.repositories.RepoUsuarios;
import com.example.Chat.Realtime.services.ChatRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;


@Controller
public class ChatController {

    @Autowired
    private RepoUsuarios repoUsuarios;

    @Autowired
    private ChatRoomService roomService;

    @Autowired
    private RepoChatRoom repoChatRoom;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/room.register/{idRoom}")
    public void subscribeRoom(
            @Payload ChatMessage message,
            @DestinationVariable("idRoom") Long idRoom
    ) throws ChatRoomNotFoundException {

        ChatRoom newChat = roomService.joinUserToRoom(idRoom, message);

        newChat.getUsuarios().forEach(usuario -> {
            messagingTemplate.convertAndSendToUser(
                    usuario.getUsername(),
                    "/queue/rooms",
                    message
            );
        });
    }

    @MessageMapping("/room.send/{idRoom}")
    public void sendMessageRoom(
            @Payload ChatMessage message,
            @DestinationVariable("idRoom") Long idRoom
    ) throws ChatRoomNotFoundException, UserNotMemberException {

        ChatRoom chatRoom = roomService.findChatRoomById(idRoom);
        Boolean isMember = roomService.checkIfUserIsMember(idRoom, message);

        if(!isMember){
            throw new UserNotMemberException("The user with the username "+ message.getSender()+ " is not a member of this chatRoom");
        }


        Usuario userOwner = this.repoUsuarios.findById(message.getSender()).get();

        Message messageInRoom = new Message();
        messageInRoom.setMessage(message.getContent());
        messageInRoom.setChatRoom(chatRoom);
        messageInRoom.setUsuario(userOwner);

        chatRoom.addMessage(messageInRoom);
        this.repoChatRoom.save(chatRoom);

        chatRoom.getUsuarios().forEach(usuario -> {
            messagingTemplate.convertAndSendToUser(
                    usuario.getUsername(),
                    "/queue/rooms",
                    message
            );
        });
    }

}
