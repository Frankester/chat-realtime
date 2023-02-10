package com.example.Chat.Realtime.controllers;

import com.example.Chat.Realtime.exeptions.ChatRoomNotFoundException;
import com.example.Chat.Realtime.exeptions.NotAdminException;
import com.example.Chat.Realtime.models.dtos.ChatRoomRequest;
import com.example.Chat.Realtime.services.ChatRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class RoomController {

    @Autowired
    ChatRoomService roomService;


    @PostMapping(path="/rooms")
    public ResponseEntity<Object> createRoom(@RequestBody ChatRoomRequest chatRoom){
        return ResponseEntity.ok(this.roomService.createChatRoom(chatRoom));
    }

    @GetMapping(path="/rooms")
    public ResponseEntity<Object> findByNombre(@RequestParam String findByNombre){
        return ResponseEntity.ok(this.roomService.findChatRoomsByNombre(findByNombre));
    }

    @DeleteMapping(path="/rooms/{roomId}")
    public ResponseEntity<Object> deleteRoom(@PathVariable("roomId")Long roomId) throws ChatRoomNotFoundException, NotAdminException {
        return ResponseEntity.ok(this.roomService.deleteChatRoom(roomId));
    }



}
