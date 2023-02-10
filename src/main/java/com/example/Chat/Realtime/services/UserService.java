package com.example.Chat.Realtime.services;

import com.example.Chat.Realtime.models.Usuario;
import com.example.Chat.Realtime.repositories.RepoUsuarios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    RepoUsuarios repoUsuarios;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<Usuario> usuarioOp = this.repoUsuarios.findById(username);

        if(usuarioOp.isEmpty()){
            throw new UsernameNotFoundException("Cannot found the user with the username: " + username);
        }

        Usuario usuario = usuarioOp.get();

        return new User(username,usuario.getPassword(),new ArrayList<>());
    }

    public Usuario getUserFromSecurityContext(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        Optional<Usuario> usuarioOp = this.repoUsuarios.findById(user.getUsername());

        if(usuarioOp.isEmpty()) throw new UsernameNotFoundException("The user "+ user.getUsername() + " not found");

       return usuarioOp.get();
    }
}
