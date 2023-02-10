package com.example.Chat.Realtime.models;

import jakarta.persistence.*;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class ChatRoom {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "usuario_chat_room",
            joinColumns = @JoinColumn(name="id_chat_room",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name="id_usuario", referencedColumnName = "username")
    )
    private List<Usuario> usuarios;

    @OneToMany(mappedBy = "chatRoom", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private  List<Message> mensajes;

    private String nombre;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="id_administrador", referencedColumnName = "username")
    private Usuario administrador;

    public ChatRoom(){
        this.usuarios = new ArrayList<>();
        this.mensajes = new ArrayList<>();
    }

    public void addUsuario(Usuario usuario){
        this.usuarios.add(usuario);
    }

    public void addMessage(Message message){
        this.mensajes.add(message);
    }
}
