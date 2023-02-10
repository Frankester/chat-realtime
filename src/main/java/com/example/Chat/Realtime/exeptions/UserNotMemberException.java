package com.example.Chat.Realtime.exeptions;

public class UserNotMemberException extends Exception {

    public UserNotMemberException(String message) {
        super(message);
    }

    public UserNotMemberException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserNotMemberException(Throwable cause) {
        super(cause);
    }

    protected UserNotMemberException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
