package com.example.Chat.Realtime.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Message {

    @Id
    @GeneratedValue
    private Long id;

    private String message;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="id_usuario", referencedColumnName = "username")
    private Usuario usuario;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private ChatRoom chatRoom;

}
