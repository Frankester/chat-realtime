package com.example.Chat.Realtime.controllers;

import com.example.Chat.Realtime.config.jwt.JwtUtil;
import com.example.Chat.Realtime.exeptions.UsuarioAlreadyExistsException;
import com.example.Chat.Realtime.models.Usuario;
import com.example.Chat.Realtime.models.dtos.LoginRequest;
import com.example.Chat.Realtime.models.dtos.SignUpRequest;
import com.example.Chat.Realtime.models.dtos.TokenDTO;
import com.example.Chat.Realtime.repositories.RepoUsuarios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class UsersController {

    @Autowired
    RepoUsuarios repoUsuarios;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping("/auth/login")
    public ResponseEntity<Object> loginUser(@RequestBody LoginRequest loginUser){

        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginUser.getUsername(), loginUser.getPassword())
        );

        String token = jwtUtil.createTokenJwt(auth);

        TokenDTO tokenResponse = new TokenDTO();
        tokenResponse.setToken(token);

        return ResponseEntity.ok(tokenResponse);
    }

    @PostMapping("/auth/sign-up")
    public ResponseEntity<Object> signupUser(@RequestBody SignUpRequest usuarioReq) throws UsuarioAlreadyExistsException {

        Optional<Usuario> usuarioOp = this.repoUsuarios.findById(usuarioReq.getUsername());

        if(usuarioOp.isPresent()){
            throw new UsuarioAlreadyExistsException("El usuario " + usuarioReq.getUsername() + " ya existe");
        }

        String textPlainPassword = usuarioReq.getPassword();

        Usuario usuario = new Usuario();
        usuario.setUsername(usuarioReq.getUsername());
        usuario.setPassword(passwordEncoder.encode(textPlainPassword));
        usuario.setEmail(usuarioReq.getEmail());

        this.repoUsuarios.save(usuario);

        return ResponseEntity.ok(usuario);
    }

}
