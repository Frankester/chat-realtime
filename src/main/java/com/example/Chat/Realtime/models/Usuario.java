package com.example.Chat.Realtime.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Usuario {

    @Id
    private String username;

    private String email;

    @JsonIgnore
    private String password;

    @ManyToMany(mappedBy = "usuarios")
    private List<ChatRoom> chatRooms;

    public Usuario() {
        this.chatRooms = new ArrayList<>();
    }

    public void addChatRoom(ChatRoom room ){
        this.chatRooms.add(room);
    }
}
