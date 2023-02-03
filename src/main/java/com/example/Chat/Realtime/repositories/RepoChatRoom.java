package com.example.Chat.Realtime.repositories;

import com.example.Chat.Realtime.models.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepoChatRoom  extends JpaRepository<ChatRoom, Long> {
}
