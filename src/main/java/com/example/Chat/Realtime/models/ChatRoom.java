package com.example.Chat.Realtime.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class ChatRoom {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToMany
    @JoinTable(
            name = "usuario_chat_room",
            joinColumns = @JoinColumn(name="id_chat_room",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name="id_usuario", referencedColumnName = "username")
    )
    private List<Usuario> usuarios;

    @OneToMany(mappedBy = "chatRoom")
    private  List<Message> mensajes;

    private String nombre;

    @ManyToOne
    @JoinColumn(name="id_administrador", referencedColumnName = "username")
    private Usuario administrador;

    public ChatRoom(){
        this.usuarios = new ArrayList<>();
        this.mensajes = new ArrayList<>();
    }
}
