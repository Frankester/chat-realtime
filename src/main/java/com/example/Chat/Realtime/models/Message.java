package com.example.Chat.Realtime.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Message {

    @Id
    @GeneratedValue
    private Long id;

    private String message;

    @ManyToOne
    @JoinColumn(name="id_usuario", referencedColumnName = "nombreDeUsuario")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private ChatRoom chatRoom;

}
