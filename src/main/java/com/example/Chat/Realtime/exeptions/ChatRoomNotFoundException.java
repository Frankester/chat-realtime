package com.example.Chat.Realtime.exeptions;

public class ChatRoomNotFoundException extends Exception{

    public ChatRoomNotFoundException(String message) {
        super(message);
    }

    public ChatRoomNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ChatRoomNotFoundException(Throwable cause) {
        super(cause);
    }

    protected ChatRoomNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
