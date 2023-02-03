package com.example.Chat.Realtime.exeptions;

public class UsuarioAlreadyExistsException extends Exception{

    public UsuarioAlreadyExistsException(String message) {
        super(message);
    }

    public UsuarioAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public UsuarioAlreadyExistsException(Throwable cause) {
        super(cause);
    }

    protected UsuarioAlreadyExistsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
