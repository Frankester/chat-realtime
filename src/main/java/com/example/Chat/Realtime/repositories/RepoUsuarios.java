package com.example.Chat.Realtime.repositories;

import com.example.Chat.Realtime.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepoUsuarios extends JpaRepository<String, Usuario> {
}
