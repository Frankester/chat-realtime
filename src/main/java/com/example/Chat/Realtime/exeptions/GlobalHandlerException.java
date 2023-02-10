package com.example.Chat.Realtime.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalHandlerException {


    @ExceptionHandler(UsuarioAlreadyExistsException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String usuarioAlreadyExists(UsuarioAlreadyExistsException ex){
        return ex.getLocalizedMessage();
    }

    @ExceptionHandler({ AuthenticationException.class })
    @ResponseBody
    public ResponseEntity<Object> handlerAuthenticationException(Exception ex){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getLocalizedMessage());
    }

    @ExceptionHandler(ChatRoomNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String chatRoomNotFound(ChatRoomNotFoundException ex){
        return ex.getLocalizedMessage();
    }

    @ExceptionHandler(NotAdminException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.FORBIDDEN)
    String userNotAdmin(NotAdminException ex){
        return ex.getLocalizedMessage();
    }

    @ExceptionHandler(UserNotMemberException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.FORBIDDEN)
    String userNotMember(UserNotMemberException ex){
        return ex.getLocalizedMessage();
    }

}
