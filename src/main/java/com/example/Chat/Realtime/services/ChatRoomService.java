package com.example.Chat.Realtime.services;

import com.example.Chat.Realtime.exeptions.ChatRoomNotFoundException;
import com.example.Chat.Realtime.exeptions.NotAdminException;
import com.example.Chat.Realtime.models.ChatMessage;
import com.example.Chat.Realtime.models.ChatRoom;
import com.example.Chat.Realtime.models.Usuario;
import com.example.Chat.Realtime.models.dtos.ChatRoomRequest;
import com.example.Chat.Realtime.repositories.RepoChatRoom;
import com.example.Chat.Realtime.repositories.RepoUsuarios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ChatRoomService {

    @Autowired
    private RepoChatRoom repoChatRoom;

    @Autowired
    private RepoUsuarios repoUsuarios;

    @Autowired
    private UserService userService;

    @Autowired
    private SimpMessagingTemplate template;

    public ChatRoom createChatRoom(ChatRoomRequest chatRoom){
        Usuario  usuario = userService.getUserFromSecurityContext();

        ChatRoom newChatRoom = new ChatRoom();
        newChatRoom.setAdministrador(usuario);
        newChatRoom.addUsuario(usuario);
        newChatRoom.setNombre(chatRoom.getNombre());

        usuario.addChatRoom(newChatRoom);

        return repoChatRoom.save(newChatRoom);
    }

    public String deleteChatRoom(Long chatRoomId) throws ChatRoomNotFoundException, NotAdminException{

        Usuario  usuario = userService.getUserFromSecurityContext();

        Optional<ChatRoom> chatRoomOp = this.repoChatRoom.findById(chatRoomId);

        if(chatRoomOp.isEmpty()) throw new ChatRoomNotFoundException("The Chat Room with id "+ chatRoomId + " not found");

        ChatRoom chatRoom = chatRoomOp.get();

        if(!chatRoom.getAdministrador().equals(usuario)){
            throw new NotAdminException("Only the administrator can delete the ChatRoom");
        }

        this.repoChatRoom.deleteById(chatRoomId);

        return "ChatRoom: "+ chatRoom.getNombre() + " deleted successfull";
    }

    public ChatRoom findChatRoomById(Long chatRoomId) throws ChatRoomNotFoundException {
        Optional<ChatRoom> chatRoomOP = this.repoChatRoom.findById(chatRoomId);

        if(chatRoomOP.isEmpty()){
            throw new ChatRoomNotFoundException("Cannot found the chatRoom with id "+ chatRoomId);
        }


        return chatRoomOP.get();
    }

    public List<ChatRoom> findChatRoomsByNombre(String nombre){
        return this.repoChatRoom.findByNombre(nombre);
    }

    public Boolean checkIfUserIsMember(Long idRoom, ChatMessage message) throws ChatRoomNotFoundException {
        ChatRoom chatRoom = this.findChatRoomById(idRoom);
        Optional<Usuario> userPresent = chatRoom.getUsuarios().stream()
                .filter(u -> u.getUsername().equalsIgnoreCase(message.getSender())).findFirst();

        if(userPresent.isEmpty()){
            Optional<Usuario> currentUser = this.repoUsuarios.findById(message.getSender());
            if(currentUser.isEmpty()){
                throw new UsernameNotFoundException("User with the username: "+message.getSender()+ " not found");
            }


            return false;
        }


        return true;
    }

    public ChatRoom joinUserToRoom(Long idRoom, ChatMessage message) throws ChatRoomNotFoundException {
        ChatRoom chatRoom = this.findChatRoomById(idRoom);

        Boolean isMember = this.checkIfUserIsMember(idRoom, message);

        if(!isMember){
            Usuario usuario = this.repoUsuarios.findById(message.getSender()).get();
            chatRoom.addUsuario(usuario);

            return this.repoChatRoom.save(chatRoom);
        }

        return chatRoom;
    }
}
